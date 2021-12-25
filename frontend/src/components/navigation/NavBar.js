import './NavBar.scss';
import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import { connect } from 'react-redux';

import {
  Box,
  IconButton,
  Toolbar,
  Typography,
  AppBar,
  Divider,
  List,
  Container,
} from '@mui/material';
import { styled } from '@mui/material/styles';
import MuiDrawer from '@mui/material/Drawer';
import MenuIcon from '@mui/icons-material/Menu';
import LogoutIcon from '@mui/icons-material/Logout';
import ChevronLeftIcon from '@mui/icons-material/ChevronLeft';

import {
  mainListItemsAdmin,
  secondListItemsAdmin,
  mainListItemsClient,
  secondaryListItemsClient,
} from './listItem';
import { logout } from 'services/api/auth';
import { signOut } from 'store/actions';
import history from 'utils/history';
import Copyright from 'components/Copyright';

const drawerWidth = 200;
const transition = 'all .5s ease';

const styleOpenDrawerForAppBar = {
  marginLeft: drawerWidth,
  width: `calc(100% - ${drawerWidth}px)`,
  transition: transition,
};

const Drawer = styled(MuiDrawer)(({ open }) => ({
  '& .MuiDrawer-paper': {
    zIndex: 0,
    position: 'relative',
    whiteSpace: 'nowrap',
    width: drawerWidth,
    transition: transition,
    ...(!open && {
      overflowX: 'hidden',
      transition: transition,
      width: '60px',
    }),
  },
}));

const NavBar = props => {
  const [openDrawer, setOpenDrawer] = useState(false);
  const { user, signOut } = props;

  const toggleDrawer = () => {
    setOpenDrawer(!openDrawer);
  };

  const onClickLogout = async () => {
    const response = logout(user);
    if (response) {
      localStorage.removeItem('token');
      signOut();
      history.push('/');
    }
  };

  return (
    <Box className="nav-bar">
      <AppBar
        className="app-bar"
        position="absolute"
        open={openDrawer}
        sx={openDrawer ? { ...styleOpenDrawerForAppBar } : null}
      >
        <Toolbar>
          {!openDrawer ? (
            <IconButton
              className="open-side-menu-icon"
              edge="start"
              color="inherit"
              onClick={toggleDrawer}
            >
              <MenuIcon />
            </IconButton>
          ) : null}
          <Typography
            className="nav-bar-title"
            component="h1"
            variant="h5"
            color="inherit"
          >
            <Link to="/" style={{ textDecoration: 'none', color: '#fff' }}>
              {' '}
              {process.env.REACT_APP_NAME}
            </Link>
          </Typography>
          <IconButton color="inherit" onClick={onClickLogout}>
            <LogoutIcon />
          </IconButton>
        </Toolbar>
      </AppBar>
      <Drawer className="drawer" variant="permanent" open={openDrawer}>
        <Toolbar className="drawer-toolbar">
          <IconButton onClick={toggleDrawer}>
            <ChevronLeftIcon />
          </IconButton>
        </Toolbar>
        <Divider />
        <List>
          {user.role === 'ADMIN' ? mainListItemsAdmin : mainListItemsClient}
        </List>
        <Divider />
        <List>
          {user.role === 'ADMIN'
            ? secondListItemsAdmin
            : secondaryListItemsClient}
        </List>
      </Drawer>
      <Box className="content-page" component="main">
        <Container sx={{ mt: 4, mb: 4 }}>
          {props.children}
          <Copyright sx={{ pt: 2, pb: 2 }} />
        </Container>
      </Box>
    </Box>
  );
};

const mapStateToProps = state => {
  return {
    user: state.auth,
  };
};

export default connect(mapStateToProps, { signOut })(NavBar);
