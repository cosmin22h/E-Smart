import React, { useState, useEffect } from 'react';

import { Paper, Box, Button } from '@mui/material';
import AddIcon from '@mui/icons-material/Add';

import { getDevices } from 'services/api/devices';
import ClosingAlert from 'components/alerts/ClosingAlert';
import DevicesTable from 'components/tables/DevicesTable';
import Pagination from 'components/Pagination';
import history from 'utils/history';

const ERR_MESSAGE_FETCH = 'Unable to fetch the devices from db!';
const ITEMS_PER_PAGE = 5;

const AdminDevicesListPage = props => {
  const [devices, setDevices] = useState([]);
  const [isNextPage, setIsNextPage] = useState(false);
  const [error, setError] = useState(null);
  const [page, setPage] = useState(1);

  useEffect(() => {
    const handleGetDevices = async () => {
      const responseDevices = await getDevices(page, ITEMS_PER_PAGE).catch(
        err => setError(ERR_MESSAGE_FETCH)
      );
      if (responseDevices) {
        const responseNextDevices = await getDevices(page + 1, ITEMS_PER_PAGE);
        setDevices(responseDevices.data);
        if (responseNextDevices) {
          setIsNextPage(responseNextDevices.data.length > 0);
        }
      }
    };
    handleGetDevices();
  }, [page]);

  const handleNextPageChange = () => {
    setPage(page + 1);
  };

  const handlePrevPageChange = () => {
    setPage(page - 1);
  };

  const handleCloseError = () => {
    setError(null);
  };

  const handleSelectDevice = device => {
    history.push(`/admin/devices/update/${device.client.id}/${device.id}`);
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
    <Box>
      <Paper sx={{ p: 2 }}>
        <DevicesTable devices={devices} onSelectDevice={handleSelectDevice} />
        <Button
          color="success"
          variant="contained"
          endIcon={<AddIcon />}
          sx={{ mt: 2 }}
          onClick={() => history.push('/admin/devices/add')}
        >
          Add
        </Button>
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

export default AdminDevicesListPage;
