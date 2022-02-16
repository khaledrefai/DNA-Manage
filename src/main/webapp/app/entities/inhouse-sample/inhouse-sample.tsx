import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { openFile, byteSize, Translate, TextFormat, getSortState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './inhouse-sample.reducer';
import { IInhouseSample } from 'app/shared/model/inhouse-sample.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const InhouseSample = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(props.location, ITEMS_PER_PAGE, 'id'), props.location.search)
  );

  const inhouseSampleList = useAppSelector(state => state.inhouseSample.entities);
  const loading = useAppSelector(state => state.inhouseSample.loading);
  const totalItems = useAppSelector(state => state.inhouseSample.totalItems);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        page: paginationState.activePage - 1,
        size: paginationState.itemsPerPage,
        sort: `${paginationState.sort},${paginationState.order}`,
      })
    );
  };

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`;
    if (props.location.search !== endURL) {
      props.history.push(`${props.location.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [paginationState.activePage, paginationState.order, paginationState.sort]);

  useEffect(() => {
    const params = new URLSearchParams(props.location.search);
    const page = params.get('page');
    const sort = params.get(SORT);
    if (page && sort) {
      const sortSplit = sort.split(',');
      setPaginationState({
        ...paginationState,
        activePage: +page,
        sort: sortSplit[0],
        order: sortSplit[1],
      });
    }
  }, [props.location.search]);

  const sort = p => () => {
    setPaginationState({
      ...paginationState,
      order: paginationState.order === ASC ? DESC : ASC,
      sort: p,
    });
  };

  const handlePagination = currentPage =>
    setPaginationState({
      ...paginationState,
      activePage: currentPage,
    });

  const handleSyncList = () => {
    sortEntities();
  };

  const { match } = props;

  return (
    <div>
      <h2 id="inhouse-sample-heading" data-cy="InhouseSampleHeading">
        <Translate contentKey="dnaManagementApplicationApp.inhouseSample.home.title">Inhouse Samples</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="dnaManagementApplicationApp.inhouseSample.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="dnaManagementApplicationApp.inhouseSample.home.createLabel">Create new Inhouse Sample</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {inhouseSampleList && inhouseSampleList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="dnaManagementApplicationApp.inhouseSample.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('sampleId')}>
                  <Translate contentKey="dnaManagementApplicationApp.inhouseSample.sampleId">Sample Id</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('empGrpNo')}>
                  <Translate contentKey="dnaManagementApplicationApp.inhouseSample.empGrpNo">Emp Grp No</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('fullNameAr')}>
                  <Translate contentKey="dnaManagementApplicationApp.inhouseSample.fullNameAr">Full Name Ar</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('fullNameEn')}>
                  <Translate contentKey="dnaManagementApplicationApp.inhouseSample.fullNameEn">Full Name En</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('natAr')}>
                  <Translate contentKey="dnaManagementApplicationApp.inhouseSample.natAr">Nat Ar</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('natEn')}>
                  <Translate contentKey="dnaManagementApplicationApp.inhouseSample.natEn">Nat En</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('uid')}>
                  <Translate contentKey="dnaManagementApplicationApp.inhouseSample.uid">Uid</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('emiratesId')}>
                  <Translate contentKey="dnaManagementApplicationApp.inhouseSample.emiratesId">Emirates Id</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('exhibit')}>
                  <Translate contentKey="dnaManagementApplicationApp.inhouseSample.exhibit">Exhibit</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('gender')}>
                  <Translate contentKey="dnaManagementApplicationApp.inhouseSample.gender">Gender</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('dateOfBirth')}>
                  <Translate contentKey="dnaManagementApplicationApp.inhouseSample.dateOfBirth">Date Of Birth</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('batchDate')}>
                  <Translate contentKey="dnaManagementApplicationApp.inhouseSample.batchDate">Batch Date</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('collectionDate')}>
                  <Translate contentKey="dnaManagementApplicationApp.inhouseSample.collectionDate">Collection Date</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('sampleNotes')}>
                  <Translate contentKey="dnaManagementApplicationApp.inhouseSample.sampleNotes">Sample Notes</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('addBy')}>
                  <Translate contentKey="dnaManagementApplicationApp.inhouseSample.addBy">Add By</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('addDate')}>
                  <Translate contentKey="dnaManagementApplicationApp.inhouseSample.addDate">Add Date</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('updateBy')}>
                  <Translate contentKey="dnaManagementApplicationApp.inhouseSample.updateBy">Update By</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('updateDate')}>
                  <Translate contentKey="dnaManagementApplicationApp.inhouseSample.updateDate">Update Date</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('attachment')}>
                  <Translate contentKey="dnaManagementApplicationApp.inhouseSample.attachment">Attachment</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="dnaManagementApplicationApp.inhouseSample.dnaProfileType">Dna Profile Type</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="dnaManagementApplicationApp.inhouseSample.projectType">Project Type</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="dnaManagementApplicationApp.inhouseSample.sampleType">Sample Type</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="dnaManagementApplicationApp.inhouseSample.workPlace">Work Place</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="dnaManagementApplicationApp.inhouseSample.sampleStatus">Sample Status</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {inhouseSampleList.map((inhouseSample, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${inhouseSample.id}`} color="link" size="sm">
                      {inhouseSample.id}
                    </Button>
                  </td>
                  <td>{inhouseSample.sampleId}</td>
                  <td>{inhouseSample.empGrpNo}</td>
                  <td>{inhouseSample.fullNameAr}</td>
                  <td>{inhouseSample.fullNameEn}</td>
                  <td>{inhouseSample.natAr}</td>
                  <td>{inhouseSample.natEn}</td>
                  <td>{inhouseSample.uid}</td>
                  <td>{inhouseSample.emiratesId}</td>
                  <td>{inhouseSample.exhibit}</td>
                  <td>{inhouseSample.gender}</td>
                  <td>
                    {inhouseSample.dateOfBirth ? (
                      <TextFormat type="date" value={inhouseSample.dateOfBirth} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {inhouseSample.batchDate ? (
                      <TextFormat type="date" value={inhouseSample.batchDate} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {inhouseSample.collectionDate ? (
                      <TextFormat type="date" value={inhouseSample.collectionDate} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{inhouseSample.sampleNotes}</td>
                  <td>{inhouseSample.addBy}</td>
                  <td>
                    {inhouseSample.addDate ? <TextFormat type="date" value={inhouseSample.addDate} format={APP_LOCAL_DATE_FORMAT} /> : null}
                  </td>
                  <td>{inhouseSample.updateBy}</td>
                  <td>
                    {inhouseSample.updateDate ? (
                      <TextFormat type="date" value={inhouseSample.updateDate} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {inhouseSample.attachment ? (
                      <div>
                        {inhouseSample.attachmentContentType ? (
                          <a onClick={openFile(inhouseSample.attachmentContentType, inhouseSample.attachment)}>
                            <Translate contentKey="entity.action.open">Open</Translate>
                            &nbsp;
                          </a>
                        ) : null}
                        <span>
                          {inhouseSample.attachmentContentType}, {byteSize(inhouseSample.attachment)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td>
                    {inhouseSample.dnaProfileType ? (
                      <Link to={`dna-profile-type/${inhouseSample.dnaProfileType.id}`}>{inhouseSample.dnaProfileType.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {inhouseSample.projectType ? (
                      <Link to={`project-type/${inhouseSample.projectType.id}`}>{inhouseSample.projectType.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {inhouseSample.sampleType ? (
                      <Link to={`sample-type/${inhouseSample.sampleType.id}`}>{inhouseSample.sampleType.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {inhouseSample.workPlace ? (
                      <Link to={`work-place/${inhouseSample.workPlace.id}`}>{inhouseSample.workPlace.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {inhouseSample.sampleStatus ? (
                      <Link to={`sample-status/${inhouseSample.sampleStatus.id}`}>{inhouseSample.sampleStatus.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${inhouseSample.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${inhouseSample.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${inhouseSample.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="dnaManagementApplicationApp.inhouseSample.home.notFound">No Inhouse Samples found</Translate>
            </div>
          )
        )}
      </div>
      {totalItems ? (
        <div className={inhouseSampleList && inhouseSampleList.length > 0 ? '' : 'd-none'}>
          <div className="justify-content-center d-flex">
            <JhiItemCount page={paginationState.activePage} total={totalItems} itemsPerPage={paginationState.itemsPerPage} i18nEnabled />
          </div>
          <div className="justify-content-center d-flex">
            <JhiPagination
              activePage={paginationState.activePage}
              onSelect={handlePagination}
              maxButtons={5}
              itemsPerPage={paginationState.itemsPerPage}
              totalItems={totalItems}
            />
          </div>
        </div>
      ) : (
        ''
      )}
    </div>
  );
};

export default InhouseSample;
