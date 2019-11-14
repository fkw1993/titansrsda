package com.titansoft.utils.aspect;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: Kevin
 * @Date: 2019/8/2 10:03
 */
@ControllerAdvice
public class ControllerExceptionConfig {
    private static final Logger logger = LoggerFactory.getLogger(ControllerExceptionConfig.class);

    /**
     * 处理 Exception 异常
     *
     * @param httpServletRequest httpServletRequest
     * @param e                  异常
     * @returns
     */
   // @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public ModelAndView exceptionHandler(HttpServletRequest httpServletRequest, Exception e) {
        logger.error("服务错误:", e);
        return new ModelAndView("msgerror");
        //return "error";
      // return "<script>Ext.Msg.alert('警告', '系统出错！');</script>";
    }

    /**
     * 处理 BusinessException 异常
     *
     * @param httpServletRequest httpServletRequest
     * @param e                  异常
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = BusinessException.class)
    public ResponseEntity businessExceptionHandler(HttpServletRequest httpServletRequest, BusinessException e) {
        logger.info("业务异常。code:" + e.getCode() + "msg:" + e.getMsg());
        return new ResponseEntity<>(e.getCode(), HttpStatus.valueOf(e.getMsg()));
    }

    public class BusinessException extends RuntimeException {
        private String code;
        private String msg;

        public BusinessException(String code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }

}

