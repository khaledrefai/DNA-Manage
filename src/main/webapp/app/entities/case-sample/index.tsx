import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import CaseSample from './case-sample';
import CaseSampleDetail from './case-sample-detail';
import CaseSampleUpdate from './case-sample-update';
import CaseSampleDeleteDialog from './case-sample-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={CaseSampleUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={CaseSampleUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={CaseSampleDetail} />
      <ErrorBoundaryRoute path={match.url} component={CaseSample} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={CaseSampleDeleteDialog} />
  </>
);

export default Routes;
