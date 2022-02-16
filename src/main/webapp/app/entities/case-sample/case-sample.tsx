import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { openFile, byteSize, Translate, TextFormat, getSortState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './case-sample.reducer';
import { ICaseSample } from 'app/shared/model/case-sample.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const CaseSample = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(props.location, ITEMS_PER_PAGE, 'id'), props.location.search)
  );

  const caseSampleList = useAppSelector(state => state.caseSample.entities);
  const loading = useAppSelector(state => state.caseSample.loading);
  const totalItems = useAppSelector(state => state.caseSample.totalItems);

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
      <h2 id="case-sample-heading" data-cy="CaseSampleHeading">
        <Translate contentKey="dnaManagementApplicationApp.caseSample.home.title">Case Samples</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="dnaManagementApplicationApp.caseSample.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="dnaManagementApplicationApp.caseSample.home.createLabel">Create new Case Sample</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {caseSampleList && caseSampleList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="dnaManagementApplicationApp.caseSample.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('sampleId')}>
                  <Translate contentKey="dnaManagementApplicationApp.caseSample.sampleId">Sample Id</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('fullNameAr')}>
                  <Translate contentKey="dnaManagementApplicationApp.caseSample.fullNameAr">Full Name Ar</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('fullNameEn')}>
                  <Translate contentKey="dnaManagementApplicationApp.caseSample.fullNameEn">Full Name En</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('natAr')}>
                  <Translate contentKey="dnaManagementApplicationApp.caseSample.natAr">Nat Ar</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('natEn')}>
                  <Translate contentKey="dnaManagementApplicationApp.caseSample.natEn">Nat En</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('uid')}>
                  <Translate contentKey="dnaManagementApplicationApp.caseSample.uid">Uid</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('emiratesId')}>
                  <Translate contentKey="dnaManagementApplicationApp.caseSample.emiratesId">Emirates Id</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('exhibit')}>
                  <Translate contentKey="dnaManagementApplicationApp.caseSample.exhibit">Exhibit</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('gender')}>
                  <Translate contentKey="dnaManagementApplicationApp.caseSample.gender">Gender</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('dateOfBirth')}>
                  <Translate contentKey="dnaManagementApplicationApp.caseSample.dateOfBirth">Date Of Birth</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('dueDate')}>
                  <Translate contentKey="dnaManagementApplicationApp.caseSample.dueDate">Due Date</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('recievedDate')}>
                  <Translate contentKey="dnaManagementApplicationApp.caseSample.recievedDate">Recieved Date</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('sampleNotes')}>
                  <Translate contentKey="dnaManagementApplicationApp.caseSample.sampleNotes">Sample Notes</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('addBy')}>
                  <Translate contentKey="dnaManagementApplicationApp.caseSample.addBy">Add By</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('addDate')}>
                  <Translate contentKey="dnaManagementApplicationApp.caseSample.addDate">Add Date</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('updateBy')}>
                  <Translate contentKey="dnaManagementApplicationApp.caseSample.updateBy">Update By</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('updateDate')}>
                  <Translate contentKey="dnaManagementApplicationApp.caseSample.updateDate">Update Date</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('attachment')}>
                  <Translate contentKey="dnaManagementApplicationApp.caseSample.attachment">Attachment</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('caseNumber')}>
                  <Translate contentKey="dnaManagementApplicationApp.caseSample.caseNumber">Case Number</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('barcodeNumber')}>
                  <Translate contentKey="dnaManagementApplicationApp.caseSample.barcodeNumber">Barcode Number</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('caseType')}>
                  <Translate contentKey="dnaManagementApplicationApp.caseSample.caseType">Case Type</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('policeStation')}>
                  <Translate contentKey="dnaManagementApplicationApp.caseSample.policeStation">Police Station</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('reportNumber')}>
                  <Translate contentKey="dnaManagementApplicationApp.caseSample.reportNumber">Report Number</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('testEndDate')}>
                  <Translate contentKey="dnaManagementApplicationApp.caseSample.testEndDate">Test End Date</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('caseNote')}>
                  <Translate contentKey="dnaManagementApplicationApp.caseSample.caseNote">Case Note</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="dnaManagementApplicationApp.caseSample.caseSampleType">Case Sample Type</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="dnaManagementApplicationApp.caseSample.sampleStatus">Sample Status</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {caseSampleList.map((caseSample, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${caseSample.id}`} color="link" size="sm">
                      {caseSample.id}
                    </Button>
                  </td>
                  <td>{caseSample.sampleId}</td>
                  <td>{caseSample.fullNameAr}</td>
                  <td>{caseSample.fullNameEn}</td>
                  <td>{caseSample.natAr}</td>
                  <td>{caseSample.natEn}</td>
                  <td>{caseSample.uid}</td>
                  <td>{caseSample.emiratesId}</td>
                  <td>{caseSample.exhibit}</td>
                  <td>{caseSample.gender}</td>
                  <td>
                    {caseSample.dateOfBirth ? (
                      <TextFormat type="date" value={caseSample.dateOfBirth} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {caseSample.dueDate ? <TextFormat type="date" value={caseSample.dueDate} format={APP_LOCAL_DATE_FORMAT} /> : null}
                  </td>
                  <td>
                    {caseSample.recievedDate ? (
                      <TextFormat type="date" value={caseSample.recievedDate} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{caseSample.sampleNotes}</td>
                  <td>{caseSample.addBy}</td>
                  <td>
                    {caseSample.addDate ? <TextFormat type="date" value={caseSample.addDate} format={APP_LOCAL_DATE_FORMAT} /> : null}
                  </td>
                  <td>{caseSample.updateBy}</td>
                  <td>
                    {caseSample.updateDate ? <TextFormat type="date" value={caseSample.updateDate} format={APP_LOCAL_DATE_FORMAT} /> : null}
                  </td>
                  <td>
                    {caseSample.attachment ? (
                      <div>
                        {caseSample.attachmentContentType ? (
                          <a onClick={openFile(caseSample.attachmentContentType, caseSample.attachment)}>
                            <Translate contentKey="entity.action.open">Open</Translate>
                            &nbsp;
                          </a>
                        ) : null}
                        <span>
                          {caseSample.attachmentContentType}, {byteSize(caseSample.attachment)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td>{caseSample.caseNumber}</td>
                  <td>{caseSample.barcodeNumber}</td>
                  <td>{caseSample.caseType}</td>
                  <td>{caseSample.policeStation}</td>
                  <td>{caseSample.reportNumber}</td>
                  <td>
                    {caseSample.testEndDate ? (
                      <TextFormat type="date" value={caseSample.testEndDate} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{caseSample.caseNote}</td>
                  <td>
                    {caseSample.caseSampleType ? (
                      <Link to={`case-sample-type/${caseSample.caseSampleType.id}`}>{caseSample.caseSampleType.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {caseSample.sampleStatus ? (
                      <Link to={`sample-status/${caseSample.sampleStatus.id}`}>{caseSample.sampleStatus.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${caseSample.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${caseSample.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
                        to={`${match.url}/${caseSample.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
              <Translate contentKey="dnaManagementApplicationApp.caseSample.home.notFound">No Case Samples found</Translate>
            </div>
          )
        )}
      </div>
      {totalItems ? (
        <div className={caseSampleList && caseSampleList.length > 0 ? '' : 'd-none'}>
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

export default CaseSample;
