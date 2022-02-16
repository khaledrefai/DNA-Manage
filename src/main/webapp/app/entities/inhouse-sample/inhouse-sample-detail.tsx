import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, openFile, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './inhouse-sample.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const InhouseSampleDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const inhouseSampleEntity = useAppSelector(state => state.inhouseSample.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="inhouseSampleDetailsHeading">
          <Translate contentKey="dnaManagementApplicationApp.inhouseSample.detail.title">InhouseSample</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{inhouseSampleEntity.id}</dd>
          <dt>
            <span id="sampleId">
              <Translate contentKey="dnaManagementApplicationApp.inhouseSample.sampleId">Sample Id</Translate>
            </span>
          </dt>
          <dd>{inhouseSampleEntity.sampleId}</dd>
          <dt>
            <span id="empGrpNo">
              <Translate contentKey="dnaManagementApplicationApp.inhouseSample.empGrpNo">Emp Grp No</Translate>
            </span>
          </dt>
          <dd>{inhouseSampleEntity.empGrpNo}</dd>
          <dt>
            <span id="fullNameAr">
              <Translate contentKey="dnaManagementApplicationApp.inhouseSample.fullNameAr">Full Name Ar</Translate>
            </span>
          </dt>
          <dd>{inhouseSampleEntity.fullNameAr}</dd>
          <dt>
            <span id="fullNameEn">
              <Translate contentKey="dnaManagementApplicationApp.inhouseSample.fullNameEn">Full Name En</Translate>
            </span>
          </dt>
          <dd>{inhouseSampleEntity.fullNameEn}</dd>
          <dt>
            <span id="natAr">
              <Translate contentKey="dnaManagementApplicationApp.inhouseSample.natAr">Nat Ar</Translate>
            </span>
          </dt>
          <dd>{inhouseSampleEntity.natAr}</dd>
          <dt>
            <span id="natEn">
              <Translate contentKey="dnaManagementApplicationApp.inhouseSample.natEn">Nat En</Translate>
            </span>
          </dt>
          <dd>{inhouseSampleEntity.natEn}</dd>
          <dt>
            <span id="uid">
              <Translate contentKey="dnaManagementApplicationApp.inhouseSample.uid">Uid</Translate>
            </span>
          </dt>
          <dd>{inhouseSampleEntity.uid}</dd>
          <dt>
            <span id="emiratesId">
              <Translate contentKey="dnaManagementApplicationApp.inhouseSample.emiratesId">Emirates Id</Translate>
            </span>
          </dt>
          <dd>{inhouseSampleEntity.emiratesId}</dd>
          <dt>
            <span id="exhibit">
              <Translate contentKey="dnaManagementApplicationApp.inhouseSample.exhibit">Exhibit</Translate>
            </span>
          </dt>
          <dd>{inhouseSampleEntity.exhibit}</dd>
          <dt>
            <span id="gender">
              <Translate contentKey="dnaManagementApplicationApp.inhouseSample.gender">Gender</Translate>
            </span>
          </dt>
          <dd>{inhouseSampleEntity.gender}</dd>
          <dt>
            <span id="dateOfBirth">
              <Translate contentKey="dnaManagementApplicationApp.inhouseSample.dateOfBirth">Date Of Birth</Translate>
            </span>
          </dt>
          <dd>
            {inhouseSampleEntity.dateOfBirth ? (
              <TextFormat value={inhouseSampleEntity.dateOfBirth} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="batchDate">
              <Translate contentKey="dnaManagementApplicationApp.inhouseSample.batchDate">Batch Date</Translate>
            </span>
          </dt>
          <dd>
            {inhouseSampleEntity.batchDate ? (
              <TextFormat value={inhouseSampleEntity.batchDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="collectionDate">
              <Translate contentKey="dnaManagementApplicationApp.inhouseSample.collectionDate">Collection Date</Translate>
            </span>
          </dt>
          <dd>
            {inhouseSampleEntity.collectionDate ? (
              <TextFormat value={inhouseSampleEntity.collectionDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="sampleNotes">
              <Translate contentKey="dnaManagementApplicationApp.inhouseSample.sampleNotes">Sample Notes</Translate>
            </span>
          </dt>
          <dd>{inhouseSampleEntity.sampleNotes}</dd>
          <dt>
            <span id="addBy">
              <Translate contentKey="dnaManagementApplicationApp.inhouseSample.addBy">Add By</Translate>
            </span>
          </dt>
          <dd>{inhouseSampleEntity.addBy}</dd>
          <dt>
            <span id="addDate">
              <Translate contentKey="dnaManagementApplicationApp.inhouseSample.addDate">Add Date</Translate>
            </span>
          </dt>
          <dd>
            {inhouseSampleEntity.addDate ? (
              <TextFormat value={inhouseSampleEntity.addDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updateBy">
              <Translate contentKey="dnaManagementApplicationApp.inhouseSample.updateBy">Update By</Translate>
            </span>
          </dt>
          <dd>{inhouseSampleEntity.updateBy}</dd>
          <dt>
            <span id="updateDate">
              <Translate contentKey="dnaManagementApplicationApp.inhouseSample.updateDate">Update Date</Translate>
            </span>
          </dt>
          <dd>
            {inhouseSampleEntity.updateDate ? (
              <TextFormat value={inhouseSampleEntity.updateDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="attachment">
              <Translate contentKey="dnaManagementApplicationApp.inhouseSample.attachment">Attachment</Translate>
            </span>
          </dt>
          <dd>
            {inhouseSampleEntity.attachment ? (
              <div>
                {inhouseSampleEntity.attachmentContentType ? (
                  <a onClick={openFile(inhouseSampleEntity.attachmentContentType, inhouseSampleEntity.attachment)}>
                    <Translate contentKey="entity.action.open">Open</Translate>&nbsp;
                  </a>
                ) : null}
                <span>
                  {inhouseSampleEntity.attachmentContentType}, {byteSize(inhouseSampleEntity.attachment)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="dnaManagementApplicationApp.inhouseSample.dnaProfileType">Dna Profile Type</Translate>
          </dt>
          <dd>{inhouseSampleEntity.dnaProfileType ? inhouseSampleEntity.dnaProfileType.id : ''}</dd>
          <dt>
            <Translate contentKey="dnaManagementApplicationApp.inhouseSample.projectType">Project Type</Translate>
          </dt>
          <dd>{inhouseSampleEntity.projectType ? inhouseSampleEntity.projectType.id : ''}</dd>
          <dt>
            <Translate contentKey="dnaManagementApplicationApp.inhouseSample.sampleType">Sample Type</Translate>
          </dt>
          <dd>{inhouseSampleEntity.sampleType ? inhouseSampleEntity.sampleType.id : ''}</dd>
          <dt>
            <Translate contentKey="dnaManagementApplicationApp.inhouseSample.workPlace">Work Place</Translate>
          </dt>
          <dd>{inhouseSampleEntity.workPlace ? inhouseSampleEntity.workPlace.id : ''}</dd>
          <dt>
            <Translate contentKey="dnaManagementApplicationApp.inhouseSample.sampleStatus">Sample Status</Translate>
          </dt>
          <dd>{inhouseSampleEntity.sampleStatus ? inhouseSampleEntity.sampleStatus.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/inhouse-sample" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/inhouse-sample/${inhouseSampleEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default InhouseSampleDetail;
