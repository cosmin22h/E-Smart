import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { connect } from 'react-redux';

import { Grid, Paper, Button } from '@mui/material';

import { selectDevice } from 'store/actions';
import { getDevicesForClient } from 'services/api/devices';
import { getReportRecords } from 'services/api/records';
import { getUnreadNo } from 'services/api/notification';
import mapRecordsToChartData from 'utils/mapRecordsToChartData';
import ClientDevicesTable from 'components/tables/ClientDevicesTable';
import Title from 'components/Title';
import history from 'utils/history';
import Chart from 'components/chart/Chart';
import Notification from 'components/Notification';

const MAX_DEVICES = 5;

const ClientHomePage = props => {
  const { selectDevice, client } = props;

  const [records, setRecords] = useState([]);
  const [devices, setDevices] = useState([]);
  const [isOpen, setIsOpen] = useState(false);
  const [noOfUnreadMessages, setNoOfUnreadMessages] = useState(0);

  useEffect(() => {
    const handleTodayReport = async () => {
      const today = new Date();
      let date = today.getFullYear() + '-' + (today.getMonth() + 1) + '-';
      if (today.getDate() < 10) {
        date += JSON.stringify(0) + today.getDate();
      } else {
        date += today.getDate();
      }
      const response = await getReportRecords(client.id, date).catch(err =>
        console.log(err.response.data.message)
      );
      if (response) {
        setRecords(response.data);
      }
    };
    const handleGetProfile = async () => {
      const response = await getDevicesForClient(
        client.id,
        1,
        MAX_DEVICES
      ).catch(err => console.log(err.response.data.message));
      if (response) {
        setDevices(response.data);
      }
    };
    handleTodayReport();
    handleGetProfile();
  }, [client]);

  useEffect(() => {
    const getIfAreUnreadMessages = async () => {
      const response = await getUnreadNo(client.id);
      if (response) {
        setIsOpen(response.data > 0);
        setNoOfUnreadMessages(response.data);
      }
    };
    getIfAreUnreadMessages();
  }, [client]);

  const handleSelectDevice = device => {
    selectDevice(device);
    history.push(`/client/my-devices/${device.id}`);
  };

  const handleOnCloseNotification = () => {
    setIsOpen(false);
  };

  return (
    <React.Fragment>
      <Notification
        message={`You have ${noOfUnreadMessages} unread messages!`}
        isOpen={isOpen}
        onClose={handleOnCloseNotification}
      />
      <Grid container spacing={3}>
        <Grid item xs={12}>
          <Paper className="paper">
            <Title>Today consumption</Title>
            <Chart data={mapRecordsToChartData(records)} />
          </Paper>
        </Grid>
        <Grid item xs={12}>
          <Paper className="paper">
            <Title>My devices</Title>
            <ClientDevicesTable
              devices={devices}
              onSelectDevice={handleSelectDevice}
            />
            <Link
              className="link"
              to="/client/my-devices"
              style={{ marginTop: '1rem', width: '100%' }}
            >
              <Button color="primary" variant="outlined" size="small" fullWidth>
                View All
              </Button>
            </Link>
          </Paper>
        </Grid>
      </Grid>
    </React.Fragment>
  );
};

const mapStateToProps = state => {
  return {
    client: state.auth,
  };
};

export default connect(mapStateToProps, { selectDevice })(ClientHomePage);
