import React from 'react';
import { Switch } from 'react-router-dom';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import MemberData from './member-data';
import InbodyData from './inbody-data';
import InBodyResultsSheet from './in-body-results-sheet';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}member-data`} component={MemberData} />
      <ErrorBoundaryRoute path={`${match.url}inbody-data`} component={InbodyData} />
      <ErrorBoundaryRoute path={`${match.url}in-body-results-sheet`} component={InBodyResultsSheet} />
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;
