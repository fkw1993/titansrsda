package com.titansoft.service.Impl;

import com.titansoft.mapper.*;
import com.titansoft.service.*;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author: Kevin
 * @Date: 2019/9/27 15:59
 */
public class BaseServiceImpl {
    @Autowired
    AuthMapper authMapper;
    @Autowired
    CadreMapper cadreMapper;
    @Autowired
    CadreTransferMapper cadreTransferMapper;
    @Autowired
    CatalogueMapper catalogueMapper;
    @Autowired
    ConfigMapper configMapper;
    @Autowired
    DaProcessMapper daProcessMapper;
    @Autowired
    DataLogMapper dataLogMapper;
    @Autowired
    DictionaryMapper dictionaryMapper;
    @Autowired
    ErrorLogMapper errorLogMapper;
    @Autowired
    LoginLogMapper loginLogMapper;
    @Autowired
    MediaSourceMapper mediaSourceMapper;
    @Autowired
    NoticeMapper noticeMapper;
    @Autowired
    OrganMapper organMapper;
    @Autowired
    PrivilegeMapper privilegeMapper;
    @Autowired
    PrivilegeViewMapper privilegeViewMapper;
    @Autowired
    ProductMapper productMapper;
    @Autowired
    RoleMapper roleMapper;
    @Autowired
    SecurityLogMapper securityLogMapper;
    @Autowired
    SortMapper sortMapper;
    @Autowired
    SqcdItemMapper sqcdItemMapper;
    @Autowired
    SqcdMapper sqcdMapper;
    @Autowired
    SqcdPowerMapper sqcdPowerMapper;
    @Autowired
    SqcdreasonMapper sqcdreasonMapper;
    @Autowired
    StoreitemMapper storeitemMapper;
    @Autowired
    StoreMapper storeMapper;
    @Autowired
    UnitMapper unitMapper;
    @Autowired
    UnitViewMapper unitViewMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    UserStatusMapper userStatusMapper;
    @Autowired
    UserViewMapper userViewMapper;
    @Autowired
    CatalogueItemMapper catalogueItemMapper;
    @Autowired
    TjLyMapper tjLyMapper;
    @Autowired
    TjLyReasonMapper tjLyReasonMapper;
    @Autowired
    PoMapper poMapper;
    @Autowired
    CommonMapper commonMapper;
    @Autowired
    SqcdStoreMapper sqcdStoreMapper;

    /***********************加载Service***********************/
    @Autowired
    CadreCJService cadreCJService;
    @Autowired
    CadreService cadreService;
    @Autowired
    CadreSHService cadreSHService;
    @Autowired
    CatalogueService catalogueService;
    @Autowired
    DesktopService desktopService;
    @Autowired
    DictionaryService dictionaryService;
    @Autowired
    LoginService loginService;
    @Autowired
    LogService logService;
    @Autowired
    MediaService mediaService;
    /* @Autowired
     OrganService organService;*/
    @Autowired
    PrivilegeService privilegeService;
    @Autowired
    UnitService unitService;
    @Autowired
    CadreStatisticsService cadreStatisticsService;
}
