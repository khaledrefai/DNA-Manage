import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, openFile, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './case-sample.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const CaseSampleDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const caseSampleEntity = useAppSelector(state => state.caseSample.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="caseSampleDetailsHeading">
          <Translate contentKey="dnaManagementApplicationApp.caseSample.detail.title">CaseSample</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{caseSampleEntity.id}</dd>
          <dt>
            <span id="sampleId">
              <Translate contentKey="dnaManagementApplicationApp.caseSample.sampleId">Sample Id</Translate>
            </span>
          </dt>
          <dd>{caseSampleEntity.sampleId}</dd>
          <dt>
            <span id="fullNameAr">
              <Translate contentKey="dnaManagementApplicationApp.caseSample.fullNameAr">Full Name Ar</Translate>
            </span>
          </dt>
          <dd>{caseSampleEntity.fullNameAr}</dd>
          <dt>
            <span id="fullNameEn">
              <Translate contentKey="dnaManagementApplicationApp.caseSample.fullNameEn">Full Name En</Translate>
            </span>
          </dt>
          <dd>{caseSampleEntity.fullNameEn}</dd>
          <dt>
            <span id="natAr">
              <Translate contentKey="dnaManagementApplicationApp.caseSample.natAr">Nat Ar</Translate>
            </span>
          </dt>
          <dd>{caseSampleEntity.natAr}</dd>
          <dt>
            <span id="natEn">
              <Translate contentKey="dnaManagementApplicationApp.caseSample.natEn">Nat En</Translate>
            </span>
          </dt>
          <dd>{caseSampleEntity.natEn}</dd>
          <dt>
            <span id="uid">
              <Translate contentKey="dnaManagementApplicationApp.caseSample.uid">Uid</Translate>
            </span>
          </dt>
          <dd>{caseSampleEntity.uid}</dd>
          <dt>
            <span id="emiratesId">
              <Translate contentKey="dnaManagementApplicationApp.caseSample.emiratesId">Emirates Id</Translate>
            </span>
          </dt>
          <dd>{caseSampleEntity.emiratesId}</dd>
          <dt>
            <span id="exhibit">
              <Translate contentKey="dnaManagementApplicationApp.caseSample.exhibit">Exhibit</Translate>
            </span>
          </dt>
          <dd>{caseSampleEntity.exhibit}</dd>
          <dt>
            <span id="gender">
              <Translate contentKey="dnaManagementApplicationApp.caseSample.gender">Gender</Translate>
            </span>
          </dt>
          <dd>{caseSampleEntity.gender}</dd>
          <dt>
            <span id="dateOfBirth">
              <Translate contentKey="dnaManagementApplicationApp.caseSample.dateOfBirth">Date Of Birth</Translate>
            </span>
          </dt>
          <dd>
            {caseSampleEntity.dateOfBirth ? (
              <TextFormat value={caseSampleEntity.dateOfBirth} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="dueDate">
              <Translate contentKey="dnaManagementApplicationApp.caseSample.dueDate">Due Date</Translate>
            </span>
          </dt>
          <dd>
            {caseSampleEntity.dueDate ? <TextFormat value={caseSampleEntity.dueDate} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="recievedDate">
              <Translate contentKey="dnaManagementApplicationApp.caseSample.recievedDate">Recieved Date</Translate>
            </span>
          </dt>
          <dd>
            {caseSampleEntity.recievedDate ? (
              <TextFormat value={caseSampleEntity.recievedDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="sampleNotes">
              <Translate contentKey="dnaManagementApplicationApp.caseSample.sampleNotes">Sample Notes</Translate>
            </span>
          </dt>
          <dd>{caseSampleEntity.sampleNotes}</dd>
          <dt>
            <span id="addBy">
              <Translate contentKey="dnaManagementApplicationApp.caseSample.addBy">Add By</Translate>
            </span>
          </dt>
          <dd>{caseSampleEntity.addBy}</dd>
          <dt>
            <span id="addDate">
              <Translate contentKey="dnaManagementApplicationApp.caseSample.addDate">Add Date</Translate>
            </span>
          </dt>
          <dd>
            {caseSampleEntity.addDate ? <TextFormat value={caseSampleEntity.addDate} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="updateBy">
              <Translate contentKey="dnaManagementApplicationApp.caseSample.updateBy">Update By</Translate>
            </span>
          </dt>
          <dd>{caseSampleEntity.updateBy}</dd>
          <dt>
            <span id="updateDate">
              <Translate contentKey="dnaManagementApplicationApp.caseSample.updateDate">Update Date</Translate>
            </span>
          </dt>
          <dd>
            {caseSampleEntity.updateDate ? (
              <TextFormat value={caseSampleEntity.updateDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="attachment">
              <Translate contentKey="dnaManagementApplicationApp.caseSample.attachment">Attachment</Translate>
            </span>
          </dt>
          <dd>
            {caseSampleEntity.attachment ? (
              <div>
                {caseSampleEntity.attachmentContentType ? (
                  <a onClick={openFile(caseSampleEntity.attachmentContentType, caseSampleEntity.attachment)}>
                    <Translate contentKey="entity.action.open">Open</Translate>&nbsp;
                  </a>
                ) : null}
                <span>
                  {caseSampleEntity.attachmentContentType}, {byteSize(caseSampleEntity.attachment)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="caseNumber">
              <Translate contentKey="dnaManagementApplicationApp.caseSample.caseNumber">Case Number</Translate>
            </span>
          </dt>
          <dd>{caseSampleEntity.caseNumber}</dd>
          <dt>
            <span id="barcodeNumber">
              <Translate contentKey="dnaManagementApplicationApp.caseSample.barcodeNumber">Barcode Number</Translate>
            </span>
          </dt>
          <dd>{caseSampleEntity.barcodeNumber}</dd>
          <dt>
            <span id="caseType">
              <Translate contentKey="dnaManagementApplicationApp.caseSample.caseType">Case Type</Translate>
            </span>
          </dt>
          <dd>{caseSampleEntity.caseType}</dd>
          <dt>
            <span id="policeStation">
              <Translate contentKey="dnaManagementApplicationApp.caseSample.policeStation">Police Station</Translate>
            </span>
          </dt>
          <dd>{caseSampleEntity.policeStation}</dd>
          <dt>
            <span id="reportNumber">
              <Translate contentKey="dnaManagementApplicationApp.caseSample.reportNumber">Report Number</Translate>
            </span>
          </dt>
          <dd>{caseSampleEntity.reportNumber}</dd>
          <dt>
            <span id="testEndDate">
              <Translate contentKey="dnaManagementApplicationApp.caseSample.testEndDate">Test End Date</Translate>
            </span>
          </dt>
          <dd>
            {caseSampleEntity.testEndDate ? (
              <TextFormat value={caseSampleEntity.testEndDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="caseNote">
              <Translate contentKey="dnaManagementApplicationApp.caseSample.caseNote">Case Note</Translate>
            </span>
          </dt>
          <dd>{caseSampleEntity.caseNote}</dd>
          <dt>
            <Translate contentKey="dnaManagementApplicationApp.caseSample.caseSampleType">Case Sample Type</Translate>
          </dt>
          <dd>{caseSampleEntity.caseSampleType ? caseSampleEntity.caseSampleType.id : ''}</dd>
          <dt>
            <Translate contentKey="dnaManagementApplicationApp.caseSample.sampleStatus">Sample Status</Translate>
          </dt>
          <dd>{caseSampleEntity.sampleStatus ? caseSampleEntity.sampleStatus.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/case-sample" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/case-sample/${caseSampleEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CaseSampleDetail;
