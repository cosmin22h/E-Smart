import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';

import { Box, Paper, TextField } from '@mui/material';
import { getReportRecords } from 'services/api/records';
import Chart from 'components/chart/Chart';
import mapRecordsToChartData from 'utils/mapRecordsToChartData';

const ClientConsumptionPage = props => {
  const { client } = props;

  const [reportDate, setReportDate] = useState('');
  const [records, setRecords] = useState([]);

  useEffect(() => {
    const handleGetReport = async () => {
      const response = await getReportRecords(client.id, reportDate).catch(
        err => console.log(err.response)
      );
      if (response) {
        setRecords(response.data);
      }
    };
    if (reportDate) {
      handleGetReport();
    }
  }, [client, reportDate]);

  return (
    <Box sx={{ p: 2 }}>
      <Paper sx={{ p: 2 }}>
        <TextField
          name="reportDate"
          label="Select a day"
          type="date"
          fullWidth
          focused
          value={reportDate}
          onChange={e => setReportDate(e.target.value)}
          sx={{ mt: 2 }}
        />
      </Paper>
      {reportDate ? (
        <Paper sx={{ p: 2, mt: 2 }}>
          <Chart data={mapRecordsToChartData(records)} />
        </Paper>
      ) : null}
    </Box>
  );
};

const mapStateToProps = state => {
  return {
    client: state.auth,
  };
};

export default connect(mapStateToProps)(ClientConsumptionPage);
