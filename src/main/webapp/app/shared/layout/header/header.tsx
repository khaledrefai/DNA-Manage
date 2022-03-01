import './header.scss';

import React, { useState, useEffect } from 'react';
import { Translate, Storage } from 'react-jhipster';
import { Navbar, Nav, NavbarToggler, Collapse } from 'reactstrap';
import LoadingBar from 'react-redux-loading-bar';

import { isRTL } from 'app/config/translation';
import { Home, Brand } from './header-components';
import { AdminMenu, EntitiesMenu, AccountMenu, LocaleMenu } from '../menus';
import { useAppDispatch } from 'app/config/store';
import { setLocale } from 'app/shared/reducers/locale';

export interface IHeaderProps {
  isAuthenticated: boolean;
  isAdmin: boolean;
  ribbonEnv: string;
  isInProduction: boolean;
  isOpenAPIEnabled: boolean;
  currentLocale: string;
}

const Header = (props: IHeaderProps) => {
  const [menuOpen, setMenuOpen] = useState(false);
  useEffect(() => document.querySelector('html').setAttribute('dir', isRTL(Storage.session.get('locale')) ? 'rtl' : 'ltr'));

  const dispatch = useAppDispatch();

  const handleLocaleChange = event => {
    const langKey = event.target.value;
    Storage.session.set('locale', langKey);
    dispatch(setLocale(langKey));
    document.querySelector('html').setAttribute('dir', isRTL(langKey) ? 'rtl' : 'ltr');
  };

  const renderDevRibbon = () =>
    props.isInProduction === false ? (
      <div className="ribbon dev">
        <a href="">
          <Translate contentKey={`global.ribbon.${props.ribbonEnv}`} />
        </a>
      </div>
    ) : null;

  const toggleMenu = () => setMenuOpen(!menuOpen);

  /* jhipster-needle-add-element-to-menu - JHipster will add new menu items here */

  return (
    <div id="app-header">
      {renderDevRibbon()}
      <header className="header-menu">
        <LoadingBar className="loading-bar" />
        <div className="header-menu-container">
          <div className="row">
            <div className="col-lg-3 col-md-3 col-sm-6 col-xs-12">
              <Brand />
            </div>
            <div className="col-lg-6 col-md-6 col-sm-6 col-xs-12 d-flex">
              
                <Nav id="header-tabs" className="header-navbar">
                  <Home />
                  {props.isAuthenticated && <EntitiesMenu />}
                  {props.isAuthenticated && props.isAdmin && (
                    <AdminMenu showOpenAPI={props.isOpenAPIEnabled} showDatabase={!props.isInProduction} />
                  )}
                  <LocaleMenu currentLocale={props.currentLocale} onClick={handleLocaleChange} />
                  <AccountMenu isAuthenticated={props.isAuthenticated} />
                </Nav>
              
            </div>
            <div className="col-lg-3 col-md-4 col-sm-6 col-xs-12">
              <div className="float-right w-100 mt-n2">
                <div className="fullscreen-switch float-right pt-3 pr-3">
                  <a id="fs-doc-button">
                    <i className="material-icons animated zoomIn">crop_free</i>
                  </a>
                  {/* <a id="fs-exit-doc-button">
                    <i className="material-icons animated zoomIn">fullscreen_exit</i>
                  </a> */}
                </div>
                <div className="user-profile float-right">
                  <div className="profile-image">
                    <span><small>ﺻﺒﺎﺡ اﻟﺨﻴﺮ </small><br />ﺳﺎﻣﻲ</span>
                    <div className="emp-image">
                      <img src="content/images/profile.png" className="profile-image-img" alt="" />
                    </div>
                  </div>
                </div>

              </div>
            </div>
          </div>
        </div>
      </header>
      {/* <LoadingBar className="loading-bar" />
      <Navbar data-cy="navbar" dark expand="md" fixed="top" className="jh-navbar">
        <NavbarToggler aria-label="Menu" onClick={toggleMenu} />
        <Brand />
        <Collapse isOpen={menuOpen} navbar>
          <Nav id="header-tabs" className="ms-auto" navbar>
            <Home />
            {props.isAuthenticated && <EntitiesMenu />}
            {props.isAuthenticated && props.isAdmin && (
              <AdminMenu showOpenAPI={props.isOpenAPIEnabled} showDatabase={!props.isInProduction} />
            )}
            <LocaleMenu currentLocale={props.currentLocale} onClick={handleLocaleChange} />
            <AccountMenu isAuthenticated={props.isAuthenticated} />
          </Nav>
        </Collapse>
      </Navbar> */}
    </div>
  );
};

export default Header;
