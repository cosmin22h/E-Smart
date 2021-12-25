import React, { useState } from 'react';

import { Grid, Paper, Tabs, Tab } from '@mui/material';
import Title from 'components/Title';
import HistoryTab from 'components/tabs/HistoryTab';
import AveragedTab from 'components/tabs/AveragedTab';
import ProgramTab from 'components/tabs/ProgramTab';

const TABS = [
  {
    value: 'history',
    label: 'Hourly Historical Consumption',
  },
  {
    value: 'averaged',
    label: 'Averaged Consumption (Last week)',
  },
  {
    value: 'Program',
    label: 'Best program for device',
  },
];

const ClientAppPage = () => {
  const [tab, setTab] = useState(TABS[0]);

  const handleTabChange = (e, newTab) => {
    setTab(newTab);
  };

  const renderTabs = () =>
    TABS.map(tabIndex => (
      <Tab key={tabIndex.value} value={tabIndex} label={tabIndex.label} />
    ));

  return (
    <Grid container spacing={3}>
      <Grid item xs={12}>
        <Paper className="paper">
          <Title>My E-Smart App</Title>
          <Tabs
            value={tab}
            onChange={handleTabChange}
            textColor="primary"
            indicatorColor="primary"
          >
            {renderTabs()}
          </Tabs>
          {tab === TABS[0] ? <HistoryTab /> : null}
          {tab === TABS[1] ? <AveragedTab /> : null}
          {tab === TABS[2] ? <ProgramTab /> : null}
        </Paper>
      </Grid>
    </Grid>
  );
};

export default ClientAppPage;
