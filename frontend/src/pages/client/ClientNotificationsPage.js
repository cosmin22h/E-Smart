import React, { useState, useEffect } from 'react';
import { useSelector } from 'react-redux';

import { Box, Paper, Typography } from '@mui/material';

import { getNotifications, readNotification } from 'services/api/notification';

import NotificationTable from 'components/tables/NotificationTable';
import Pagination from 'components/Pagination';
import ClosingAlert from 'components/alerts/ClosingAlert';
import NotificationModal from 'components/modals/NotificationModal';

const ERR_MESSAGE =
  'Unable to fetch your notification. Please contact an admin and report this problem!';
const ITEMS_PER_PAGE = 20;

const ClientNotificationsPage = props => {
  const client = useSelector(state => state.auth);

  const [notifications, setNotifications] = useState([]);
  const [isNextPage, setIsNextPage] = useState(false);
  const [error, setError] = useState(null);
  const [page, setPage] = useState(1);
  const [isOpen, setIsOpen] = useState(false);
  const [selectedNotification, setSelectedNotification] = useState({});
  const [refresh, setRefresh] = useState(false);

  useEffect(() => {
    const handleGetNotifications = async () => {
      const responseCurrentNotification = await getNotifications(
        client.id,
        page,
        ITEMS_PER_PAGE
      ).catch(err => setError(ERR_MESSAGE));
      if (responseCurrentNotification) {
        setNotifications(responseCurrentNotification.data);
        const responseNextNotifications = await getNotifications(
          client.id,
          page + 1,
          ITEMS_PER_PAGE
        ).catch(err => setError(ERR_MESSAGE));
        if (responseNextNotifications) {
          setIsNextPage(responseNextNotifications.data.length > 0);
        }
      }
    };
    handleGetNotifications();
  }, [client, page, refresh]);

  const handleNextPageChange = () => {
    setPage(page + 1);
  };

  const handlePrevPageChange = () => {
    setPage(page - 1);
  };

  const handleCloseError = () => {
    setError(null);
  };

  const handleRead = notification => {
    setIsOpen(true);
    setSelectedNotification(notification);
  };

  const handleCloseModal = async () => {
    const response = await readNotification(selectedNotification.id).catch(
      err => console.log(err)
    );
    if (response) {
      setIsOpen(false);
      setRefresh(!refresh);
    }
  };

  const renderModalBody = () => (
    <React.Fragment>
      <Typography>
        Measured value:{' '}
        {selectedNotification.record.energyConsumption.toFixed(2)}
      </Typography>
      <Typography>Timestamp: {selectedNotification.timestamp}</Typography>
      <Typography>
        Sensor ID: {selectedNotification.device.sensor.id}
      </Typography>
      <Typography>Device: {selectedNotification.device.description}</Typography>
      <Typography>
        Device location: {selectedNotification.device.location}
      </Typography>
    </React.Fragment>
  );

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
    <React.Fragment>
      {isOpen ? (
        <NotificationModal
          title={`Warning! ${selectedNotification.device.sensor.description} exceed a measurement!`}
          bodyText={renderModalBody()}
          btnText="OK"
          open={isOpen}
          onClose={handleCloseModal}
        />
      ) : null}
      <Box>
        <Paper sx={{ p: 2 }}>
          <NotificationTable
            notifications={notifications}
            onSelectNotification={handleRead}
          />
        </Paper>
        <Pagination
          currentPage={page}
          isNextPage={isNextPage}
          onPrevPage={handlePrevPageChange}
          onNextPage={handleNextPageChange}
        />
      </Box>
    </React.Fragment>
  );
};

export default ClientNotificationsPage;
