import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import DnaProfileType from './dna-profile-type';
import DnaProfileTypeDetail from './dna-profile-type-detail';
import DnaProfileTypeUpdate from './dna-profile-type-update';
import DnaProfileTypeDeleteDialog from './dna-profile-type-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={DnaProfileTypeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={DnaProfileTypeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={DnaProfileTypeDetail} />
      <ErrorBoundaryRoute path={match.url} component={DnaProfileType} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={DnaProfileTypeDeleteDialog} />
  </>
);

export default Routes;
