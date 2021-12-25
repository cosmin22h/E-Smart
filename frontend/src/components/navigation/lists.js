import DashboardIcon from '@mui/icons-material/Dashboard';
import PeopleAltIcon from '@mui/icons-material/PeopleAlt';
import WbIncandescentIcon from '@mui/icons-material/WbIncandescent';
import HomeIcon from '@mui/icons-material/Home';
import LockIcon from '@mui/icons-material/Lock';
import EventNoteIcon from '@mui/icons-material/EventNote';
import SpeedIcon from '@mui/icons-material/Speed';
import NotificationsActiveIcon from '@mui/icons-material/NotificationsActive';
import AppsIcon from '@mui/icons-material/Apps';

export const mainListAdmin = [
  {
    icon: <DashboardIcon color="primary" />,
    text: 'Dashboard',
    link: '/admin',
  },
  {
    icon: <PeopleAltIcon color="primary" />,
    text: 'Clients',
    link: '/admin/clients',
  },
  {
    icon: <WbIncandescentIcon color="primary" />,
    text: 'Devices',
    link: '/admin/devices',
  },
  {
    icon: <LockIcon color="primary" />,
    text: 'My Profile',
    link: '/admin/my-profile',
  },
];

export const secondListAdmin = [
  {
    icon: <SpeedIcon color="primary" />,
    text: 'Sensors',
    link: '/admin/sensor/export',
  },
];

export const mainListClient = [
  {
    icon: <HomeIcon color="primary" />,
    text: 'Home',
    link: '/client',
  },
  {
    icon: <WbIncandescentIcon color="primary" />,
    text: 'My devices',
    link: '/client/my-devices',
  },
  {
    icon: <AppsIcon color="primary" />,
    text: 'My App',
    link: '/client/my-app',
  },
];

export const secondListClient = [
  {
    icon: <NotificationsActiveIcon color="primary" />,
    text: 'Notification',
    link: '/client/notification',
  },
  {
    icon: <EventNoteIcon color="primary" />,
    text: 'Consumption',
    link: '/client/consumption',
  },
];
