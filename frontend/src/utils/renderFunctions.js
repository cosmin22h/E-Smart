import { Typography } from '@mui/material';
import CircleIcon from '@mui/icons-material/Circle';
import CheckCircleOutlineIcon from '@mui/icons-material/CheckCircleOutline';

export const renderOnline = lastSession =>
  lastSession ? (
    lastSession.logoutTime ? (
      lastSession.logoutTime
    ) : (
      <Typography color="secondary">ONLINE</Typography>
    )
  ) : (
    <Typography>INACTIVE</Typography>
  );

export const renderAddress = client =>
  `${client.address.addressOne}, ${client.address.region}, ${client.address.country}`;

export const renderInactiveIcon = <CircleIcon color="error" />;

export const renderActiveIcon = <CheckCircleOutlineIcon color="success" />;
