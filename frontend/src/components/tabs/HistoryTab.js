import React, { useState } from 'react';
import { useSelector } from 'react-redux';

import { Box, TextField, Button } from '@mui/material';
import { getHistoricalConsumption } from 'services/api/rpc';
import StackedBarChart from 'components/chart/StackedBarChart';

const HistoryTab = props => {
  const auth = useSelector(state => state.auth);
  const [days, setDays] = useState(7);
  const [historyData, setHistoryData] = useState([]);

  const handleDataMapping = data => {
    setHistoryData(
      data.map((d, i) => {
        return { hour: i, ...d };
      })
    );
  };

  const handleOnClickShowGraph = async () => {
    const response = await getHistoricalConsumption(auth.id, days);
    if (response) {
      handleDataMapping(response.data.result);
    }
  };

  return (
    <Box sx={{ p: 2 }}>
      <TextField
        value={days}
        onChange={e => setDays(e.target.value)}
        name="day"
        label="Select the number of days"
        type="number"
        InputProps={{ inputProps: { min: 1 } }}
        fullWidth
        sx={{ mt: 2 }}
      />
      <Button
        variant="contained"
        sx={{ mt: 2, width: '100%' }}
        onClick={handleOnClickShowGraph}
      >
        SHOW GRAPH
      </Button>
      {historyData.length > 0 ? <StackedBarChart data={historyData} /> : null}
    </Box>
  );
};

export default HistoryTab;
