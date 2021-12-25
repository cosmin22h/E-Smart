import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';

import { Box, Paper } from '@mui/material';

import { getDevicesForClient } from 'services/api/devices';
import { selectDevice } from 'store/actions';

import Pagination from 'components/Pagination';
import ClosingAlert from 'components/alerts/ClosingAlert';
import ClientDevicesTable from 'components/tables/ClientDevicesTable';
import history from 'utils/history';

const ERR_MESSAGE =
  'Unable to fetch your devices. Please contact an admin and report this problem!';
const ITEMS_PER_PAGE = 5;

const ClientDevicesPage = props => {
  const { client, selectDevice } = props;

  const [devices, setDevices] = useState([]);
  const [isNextPage, setIsNextPage] = useState(false);
  const [error, setError] = useState(null);
  const [page, setPage] = useState(1);

  useEffect(() => {
    const handleGetDevices = async () => {
      const responseCurrentDevices = await getDevicesForClient(
        client.id,
        page,
        ITEMS_PER_PAGE
      ).catch(err => setError(ERR_MESSAGE));
      if (responseCurrentDevices) {
        setDevices(responseCurrentDevices.data);
        const responseNextDevices = await getDevicesForClient(
          client.id,
          page + 1,
          ITEMS_PER_PAGE
        ).catch(err => setError(ERR_MESSAGE));
        if (responseNextDevices) {
          setIsNextPage(responseNextDevices.data.length > 0);
        }
      }
    };

    handleGetDevices();
  }, [client, page]);

  const handleNextPageChange = () => {
    setPage(page + 1);
  };

  const handlePrevPageChange = () => {
    setPage(page - 1);
  };

  const handleSelectDevice = device => {
    selectDevice(device);
    history.push(`/client/my-devices/${device.id}`);
  };

  const handleCloseError = () => {
    setError(null);
  };

  if (error) {
    return (
      <ClosingAlert
        severity="error"
        message={error}
        onClose={handleCloseError}
      />
    );
  }

  return (
    <Box>
      <Paper sx={{ p: 2 }}>
        <ClientDevicesTable
          devices={devices}
          onSelectDevice={handleSelectDevice}
        />
      </Paper>
      <Pagination
        currentPage={page}
        isNextPage={isNextPage}
        onPrevPage={handlePrevPageChange}
        onNextPage={handleNextPageChange}
      />
    </Box>
  );
};

const mapStateToProps = state => {
  return {
    client: state.auth,
  };
};

export default connect(mapStateToProps, { selectDevice })(ClientDevicesPage);
