import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import CaseSampleType from './case-sample-type';
import CaseSampleTypeDetail from './case-sample-type-detail';
import CaseSampleTypeUpdate from './case-sample-type-update';
import CaseSampleTypeDeleteDialog from './case-sample-type-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={CaseSampleTypeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={CaseSampleTypeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={CaseSampleTypeDetail} />
      <ErrorBoundaryRoute path={match.url} component={CaseSampleType} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={CaseSampleTypeDeleteDialog} />
  </>
);

export default Routes;
