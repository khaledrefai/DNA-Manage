import 'react-toastify/dist/ReactToastify.css';
import './app.scss';
import 'app/config/dayjs.ts';

import React, { useEffect } from 'react';
import { Card } from 'reactstrap';
import { BrowserRouter as Router } from 'react-router-dom';
import { ToastContainer, toast } from 'react-toastify';

import { useAppDispatch, useAppSelector } from 'app/config/store';
import { getSession } from 'app/shared/reducers/authentication';
import { getProfile } from 'app/shared/reducers/application-profile';
import { setLocale } from 'app/shared/reducers/locale';
import Header from 'app/shared/layout/header/header';
import Footer from 'app/shared/layout/footer/footer';
import { hasAnyAuthority } from 'app/shared/auth/private-route';
import ErrorBoundary from 'app/shared/error/error-boundary';
import { AUTHORITIES } from 'app/config/constants';
import AppRoutes from 'app/routes';

const baseHref = document.querySelector('base').getAttribute('href').replace(/\/$/, '');

export const App = () => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getSession());
    dispatch(getProfile());
  }, []);

  const currentLocale = useAppSelector(state => state.locale.currentLocale);
  const isAuthenticated = useAppSelector(state => state.authentication.isAuthenticated);
  const isAdmin = useAppSelector(state => hasAnyAuthority(state.authentication.account.authorities, [AUTHORITIES.ADMIN]));
  const ribbonEnv = useAppSelector(state => state.applicationProfile.ribbonEnv);
  const isInProduction = useAppSelector(state => state.applicationProfile.inProduction);
  const isOpenAPIEnabled = useAppSelector(state => state.applicationProfile.isOpenAPIEnabled);

  const paddingTop = '60px';
  return (
    <Router basename={baseHref}>
      <div className="app-container" style={{ paddingTop }}>
        <ToastContainer position={toast.POSITION.TOP_LEFT} className="toastify-container" toastClassName="toastify-toast" />
        <ErrorBoundary>
          <Header
            isAuthenticated={isAuthenticated}
            isAdmin={isAdmin}
            currentLocale={currentLocale}
            ribbonEnv={ribbonEnv}
            isInProduction={isInProduction}
            isOpenAPIEnabled={isOpenAPIEnabled}
          />
        </ErrorBoundary>
        <div id="app-view-container" className="main sticky sticky-cont-scroll">
          <div className="inner-container services">
            <div className="sticky-cont" id="sticky-cont">
              <div className="wrapper">
                {/* <div id="sidebar-right" className="sidebar-right sidebar-sm"> */}
                <div id="sidebar-right" className="sidebar-right">
                  <div className="main-logo d-flex">
                    <a href="javascript:(void);">
                      <img src="content/images/app-logo.png" loading="eager" />
                      <span>اﻻﻓﺘﺮاﺿﻴﺔ</span>
                    </a>
                  </div>

                  <div className="scroll-inside">
                    <div id="accordian" className="accordian">
                      <ul>
                        <li className='active'>
                          <h3>
                            <a href="javascript:(void);" data-tooltip="اﻻﻓﺘﺮاﺿﻴﺔ اﻻﻓﺘﺮاﺿﻴﺔاﻻﻓﺘﺮاﺿﻴﺔ اﻻﻓﺘﺮاﺿﻴﺔ اﻻﻓﺘﺮاﺿﻴﺔ اﻻﻓﺘﺮاﺿﻴﺔ">
                              <i className="icon ico-dp-production-card mt-n2"></i>
                              <label>اﻻﻓﺘﺮاﺿﻴﺔ اﻻﻓﺘﺮاﺿﻴﺔ</label>
                              <i className="fa fa-chevron-down left_icon"></i>
                              <span>10</span>
                            </a>
                          </h3>
                          <ul className="parent d-block">
                            <li><a href="javascript:(void);"><i className="fa fa-square icon"></i><label> اﻟﻤﻬﺎﻡ اﻟﻨﺸﻄﺔ اﻟﻤﻬﺎﻡ اﻟﻨﺸﻄﺔاﻟﻤﻬﺎﻡ اﻟﻨﺸﻄﺔ</label></a></li>
                            <li className='active'><a href="javascript:(void);"><i className="fa fa-square icon"></i><label>اﻻﻓﺘﺮاﺿﻴﺔ</label></a></li>
                            <li><a href="javascript:(void);"><i className="fa fa-square icon"></i><label>اﻟﻤﻬﺎﻡ اﻟﻨﺸﻄﺔ</label></a></li>
                            <li><a href="javascript:(void);"><i className="fa fa-square icon"></i><label>اﻻﻓﺘﺮاﺿﻴﺔ</label></a></li>
                          </ul>
                        </li>
                        <li>
                          <h3>
                            <a href="javascript:(void);" data-tooltip="اﻻﻓﺘﺮاﺿﻴﺔ اﻻﻓﺘﺮاﺿﻴﺔاﻻﻓﺘﺮاﺿﻴﺔ اﻻﻓﺘﺮاﺿﻴﺔ اﻻﻓﺘﺮاﺿﻴﺔ اﻻﻓﺘﺮاﺿﻴﺔ">
                              <i className="icon ico-dp-production-card mt-n2"></i>
                              <label>اﻻﻓﺘﺮاﺿﻴﺔ اﻻﻓﺘﺮاﺿﻴﺔ</label>
                              <i className="fa fa-chevron-down left_icon"></i>
                              <span>10</span>
                            </a>
                          </h3>
                          <ul>
                            <li><a href="javascript:(void);"><i className="fa fa-square icon"></i><label> اﻟﻤﻬﺎﻡ اﻟﻨﺸﻄﺔ اﻟﻤﻬﺎﻡ اﻟﻨﺸﻄﺔاﻟﻤﻬﺎﻡ اﻟﻨﺸﻄﺔ</label></a></li>
                            <li><a href="javascript:(void);"><i className="fa fa-square icon"></i><label>اﻻﻓﺘﺮاﺿﻴﺔ</label></a></li>
                            <li><a href="javascript:(void);"><i className="fa fa-square icon"></i><label>اﻟﻤﻬﺎﻡ اﻟﻨﺸﻄﺔ</label></a></li>
                            <li><a href="javascript:(void);"><i className="fa fa-square icon"></i><label>اﻻﻓﺘﺮاﺿﻴﺔ</label></a></li>
                          </ul>
                        </li>
                        {/* <li>
                          <h3>
                            <a href="javascript:(void);" data-tooltip="اﻟﻤﻬﺎﻡ اﻟﻨﺸﻄﺔ">
                              <i className="icon ico-dp-production-card"></i>
                              <label>اﻟﻤﻬﺎﻡ اﻟﻨﺸﻄﺔ</label>
                              <i className="fa fa-chevron-down left_icon"></i>
                            </a>
                          </h3>
                          <ul className="parent">
                            <li>
                              <a href="javascript:(void);">
                                <i className="icon ico-dp-production-card"></i>
                                <label>اﻟﻤﻬﺎﻡ اﻟﻨﺸﻄﺔ</label>
                                <i className="fa fa-chevron-down left_icon"></i>
                              </a>
                              <ul className="child">
                                <li><a href="javascript:(void);"><i className="fa fa-square icon"></i><label>اﻻﻓﺘﺮاﺿﻴﺔ</label></a></li>
                                <li><a href="javascript:(void);"><i className="fa fa-square icon"></i><label>اﻻﻓﺘﺮاﺿﻴﺔ</label></a></li>
                                <li><a href="javascript:(void);"><i className="fa fa-square icon"></i><label>اﻟﻤﻬﺎﻡ اﻟﻨﺸﻄﺔ</label></a></li>
                              </ul>
                            </li>
                            <li><a href="javascript:(void);"><i className="fa fa-square icon"></i><label>اﻻﻓﺘﺮاﺿﻴﺔ</label></a></li>
                            <li><a href="javascript:(void);"><i className="fa fa-square icon"></i><label>اﻟﻤﻬﺎﻡ اﻟﻨﺸﻄﺔ</label></a></li>
                          </ul>
                        </li>
                        <li>
                          <h3>
                            <a href="javascript:(void);" data-tooltip="Favourites">
                              <i className="icon ico-upload-cloud"></i>
                              <label>Favourites</label>
                              <i className="fa fa-chevron-down left_icon"></i>
                              <span>10</span>
                            </a>
                          </h3>
                          <ul className="parent">
                            <li><a href="javascript:(void);"><i className="fa fa-square icon"></i><label>اﻟﻤﻬﺎﻡ اﻟﻨﺸﻄﺔ</label></a></li>
                            <li><a href="javascript:(void);"><i className="fa fa-square icon"></i><label>اﻻﻓﺘﺮاﺿﻴﺔ</label></a></li>
                            <li><a href="javascript:(void);"><i className="fa fa-square icon"></i><label>اﻟﻤﻬﺎﻡ اﻟﻨﺸﻄﺔ</label></a></li>
                            <li><a href="javascript:(void);"><i className="fa fa-square icon"></i><label>اﻻﻓﺘﺮاﺿﻴﺔ</label></a></li>
                          </ul>
                        </li>
                        <li>
                          <h3><a href="javascript:(void);"><i className="fa fa-lg fa-heart"></i><label>Favourites</label><i className="fa fa-chevron-down left_icon"></i><span>10</span></a></h3>
                          <ul className="parent">
                            <li><a href="javascript:(void);"><i className="fa fa-square icon"></i><label>اﻟﻤﻬﺎﻡ اﻟﻨﺸﻄﺔ</label></a></li>
                            <li><a href="javascript:(void);"><i className="fa fa-square icon"></i><label>اﻻﻓﺘﺮاﺿﻴﺔ</label></a></li>
                            <li><a href="javascript:(void);"><i className="fa fa-square icon"></i><label>اﻟﻤﻬﺎﻡ اﻟﻨﺸﻄﺔ</label></a></li>
                            <li><a href="javascript:(void);"><i className="fa fa-square icon"></i><label>اﻻﻓﺘﺮاﺿﻴﺔ</label></a></li>
                          </ul>
                        </li>
                        <li>
                          <h3><a href="javascript:(void);"><i className="fa fa-lg fa-heart"></i><label>Favourites</label><i className="fa fa-chevron-down left_icon"></i><span>10</span></a></h3>
                          <ul className="parent">
                            <li><a href="javascript:(void);"><i className="fa fa-square icon"></i><label>اﻟﻤﻬﺎﻡ اﻟﻨﺸﻄﺔ</label></a></li>
                            <li><a href="javascript:(void);"><i className="fa fa-square icon"></i><label>اﻻﻓﺘﺮاﺿﻴﺔ</label></a></li>
                            <li><a href="javascript:(void);"><i className="fa fa-square icon"></i><label>اﻟﻤﻬﺎﻡ اﻟﻨﺸﻄﺔ</label></a></li>
                            <li><a href="javascript:(void);"><i className="fa fa-square icon"></i><label>اﻻﻓﺘﺮاﺿﻴﺔ</label></a></li>
                          </ul>
                        </li> */}
                      </ul>
                    </div>
                  </div>
                  <div className="footer-settings">
                    <a href="javascript:(void);">
                      <i className="fas fa-cog"></i>
                      <label>اﻻﻓﺘﺮاﺿﻴﺔ</label>
                    </a>
                  </div>
                </div>
                {/* right-side-bar */}
                <div id="content" className="content-main">
                  
                  <div className="content-header position-relative">
                    <div className="float-right">
                      <button type="button" id="sidebarCollapse" className="collapse-right-btn">
                        <i className="ico-right-open-3 keyboard_arrow_right nav-close d-none"></i>
                        <i className="ico-menu-1 keyboard_dehaze nav-open"></i>
                      </button>
                    </div>
                    <nav aria-label="breadcrumb">
                      <ol className="breadcrumb mr-3">
                        <li className="breadcrumb-item">اﻟﺮﺋﻴﺴﻴﺔ</li>
                        <li className="breadcrumb-item">اﻟﻌﻨﺎﻭﻳﻦ اﻻﻓﺘﺮاﺿﻴﺔ</li>
                        <li className="breadcrumb-item active">اﻟﻌﻨﺎﻭﻳﻦ اﻻﻓﺘﺮاﺿﻴﺔ</li>
                      </ol>
                    </nav>
                  </div>
                  
                  
                  <div className="scroll-content">
                  
                    <div className="contenet-area">
                      
                      <div className="row">
                        <div className="col-xl-12 col-lg-12 col-12 pr-2 pl-2 mb-3">
                          {/* Inner Content Start */}
                          <Card className="card-block">
                            <ErrorBoundary>
                              <AppRoutes />
                            </ErrorBoundary>
                          </Card>
                          <Footer />
                          {/* Inner Content End */}
                        </div>
                      </div>
                      
                    </div>
                    
                  </div>
                  
                </div>
              </div>
            </div>

            

          </div>
        </div >

      </div >
    </Router >
  );
};

export default App;
