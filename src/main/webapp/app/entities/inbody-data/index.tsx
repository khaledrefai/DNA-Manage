import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import InbodyData from './inbody-data';
import InbodyDataDetail from './inbody-data-detail';
import InbodyDataUpdate from './inbody-data-update';
import InbodyDataDeleteDialog from './inbody-data-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={InbodyDataUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={InbodyDataUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={InbodyDataDetail} />
      <ErrorBoundaryRoute path={match.url} component={InbodyData} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={InbodyDataDeleteDialog} />
  </>
);

export default Routes;
