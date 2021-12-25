import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { useParams } from 'react-router-dom';

import { Box, Paper } from '@mui/material';

import { selectDevice, unselectDevice } from 'store/actions';
import { deleteDevice, getDevicesForClient } from 'services/api/devices';
import ClosingAlert from 'components/alerts/ClosingAlert';
import ClientDevicesTable from 'components/tables/ClientDevicesTable';
import FooterTable from 'components/tables/FooterTable';
import Pagination from 'components/Pagination';
import ConfirmationModal from 'components/modals/ConfirmationModal';
import history from 'utils/history';

const ERR_MESSAGE_FETCH = 'Unable to fetch the devices from db!';
const ITEMS_PER_PAGE = 5;

const AdminClientDevicesListPage = props => {
  const { device, selectDevice, unselectDevice } = props;
  const { id } = useParams();

  const [devices, setDevices] = useState([]);
  const [isNextPage, setIsNextPage] = useState(false);
  const [selectedDevice, setSelectedDevice] = useState(device);
  const [error, setError] = useState(null);
  const [page, setPage] = useState(1);
  const [isDeleteOpen, setIsDeleteOpen] = useState(false);
  const [refresh, setRefresh] = useState(false);

  useEffect(() => {
    const handleGetDevices = async () => {
      const responseDevices = await getDevicesForClient(
        id,
        page,
        ITEMS_PER_PAGE
      ).catch(err => setError(ERR_MESSAGE_FETCH));
      if (responseDevices) {
        setDevices(responseDevices.data);
        const responseNextDevices = await getDevicesForClient(
          id,
          page + 1,
          ITEMS_PER_PAGE
        ).catch(err => setError(ERR_MESSAGE_FETCH));
        if (responseNextDevices) {
          setIsNextPage(responseNextDevices.data.length > 0);
        }
      }
    };
    handleGetDevices();
  }, [page, refresh, id]);

  useEffect(() => {
    setSelectedDevice(device);
  }, [device]);

  const handleNextPageChange = () => {
    setPage(page + 1);
    handleCancelSelectedDevice();
  };

  const handlePrevPageChange = () => {
    setPage(page - 1);
    handleCancelSelectedDevice();
  };

  const handleSelectDevice = device => {
    selectDevice(device);
  };

  const handleCancelSelectedDevice = () => {
    unselectDevice();
  };

  const handleOpenModalDeleteDevice = () => {
    setIsDeleteOpen(true);
  };

  const handleCloseModalDeleteDevice = () => {
    setIsDeleteOpen(false);
  };

  const handleCloseError = () => {
    setError(null);
  };

  const handleAddDevice = () => {
    history.push(`/admin/devices/add/${id}`);
  };

  const handleEditDevice = () => {
    history.push(`/admin/devices/update/${id}/${selectedDevice.id}`);
  };

  const handleDeleteDevice = async () => {
    const response = await deleteDevice(selectedDevice.id).catch(err =>
      setError(err.response.data.message)
    );
    if (response) {
      setRefresh(!refresh);
      setIsDeleteOpen(false);
      unselectDevice();
    }
  };

  if (error) {
    return (
      <ClosingAlert
        severity="warning"
        message={error}
        onClose={handleCloseError}
      />
    );
  }

  return (
    <React.Fragment>
      <Box>
        <Paper sx={{ p: 2 }}>
          <ClientDevicesTable
            devices={devices}
            selectedDevice={selectedDevice}
            onSelectDevice={handleSelectDevice}
          />
          <FooterTable
            selectedItem={selectedDevice}
            onAddItem={handleAddDevice}
            onCancelSelectItem={handleCancelSelectedDevice}
            onDeleteItem={handleOpenModalDeleteDevice}
            onEditItem={handleEditDevice}
          />
        </Paper>
        <Pagination
          currentPage={page}
          isNextPage={isNextPage}
          onPrevPage={handlePrevPageChange}
          onNextPage={handleNextPageChange}
        />
      </Box>
      {selectedDevice ? (
        <ConfirmationModal
          title="Are you sure you want to delete this device?"
          btnTextConfirm="Delete"
          open={isDeleteOpen}
          onConfirm={handleDeleteDevice}
          onClose={handleCloseModalDeleteDevice}
        />
      ) : null}
    </React.Fragment>
  );
};

const mapStateToProps = state => {
  return {
    device: state.selectedDevice,
  };
};

export default connect(mapStateToProps, { selectDevice, unselectDevice })(
  AdminClientDevicesListPage
);
