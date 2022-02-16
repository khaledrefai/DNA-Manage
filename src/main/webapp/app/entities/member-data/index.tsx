import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import MemberData from './member-data';
import MemberDataDetail from './member-data-detail';
import MemberDataUpdate from './member-data-update';
import MemberDataDeleteDialog from './member-data-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={MemberDataUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={MemberDataUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={MemberDataDetail} />
      <ErrorBoundaryRoute path={match.url} component={MemberData} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={MemberDataDeleteDialog} />
  </>
);

export default Routes;
