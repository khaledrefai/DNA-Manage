import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import InhouseSample from './inhouse-sample';
import InhouseSampleDetail from './inhouse-sample-detail';
import InhouseSampleUpdate from './inhouse-sample-update';
import InhouseSampleDeleteDialog from './inhouse-sample-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={InhouseSampleUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={InhouseSampleUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={InhouseSampleDetail} />
      <ErrorBoundaryRoute path={match.url} component={InhouseSample} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={InhouseSampleDeleteDialog} />
  </>
);

export default Routes;
