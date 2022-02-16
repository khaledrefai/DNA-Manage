import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';
import { Translate, translate } from 'react-jhipster';
import { NavDropdown } from './menu-components';

export const EntitiesMenu = props => (
  <NavDropdown
    icon="th-list"
    name={translate('global.menu.entities.main')}
    id="entity-menu"
    data-cy="entity"
    style={{ maxHeight: '80vh', overflow: 'auto' }}
  >
    <>{/* to avoid warnings when empty */}</>
    <MenuItem icon="asterisk" to="/member-data">
      <Translate contentKey="global.menu.entities.memberData" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/inbody-data">
      <Translate contentKey="global.menu.entities.inbodyData" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/in-body-results-sheet">
      <Translate contentKey="global.menu.entities.inBodyResultsSheet" />
    </MenuItem>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);
