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
    <MenuItem icon="asterisk" to="/inhouse-sample">
      <Translate contentKey="global.menu.entities.inhouseSample" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/dna-profile-type">
      <Translate contentKey="global.menu.entities.dnaProfileType" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/project-type">
      <Translate contentKey="global.menu.entities.projectType" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/sample-type">
      <Translate contentKey="global.menu.entities.sampleType" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/work-place">
      <Translate contentKey="global.menu.entities.workPlace" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/sample-status">
      <Translate contentKey="global.menu.entities.sampleStatus" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/case-sample">
      <Translate contentKey="global.menu.entities.caseSample" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/case-sample-type">
      <Translate contentKey="global.menu.entities.caseSampleType" />
    </MenuItem>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);
