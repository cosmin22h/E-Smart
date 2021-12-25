import React, { useState, useEffect, useCallback } from 'react';
import { Router, Route, Switch, Redirect } from 'react-router-dom';
import { useSelector } from 'react-redux';

import * as SockJS from 'sockjs-client';
import * as Stomp from 'stompjs';

import history from 'utils/history';
import requireAuth from 'components/guards/requireAuth';
import NavBar from 'components/navigation/NavBar';
import Notification from 'components/Notification';
import ClientConsumptionPage from './ClientConsumptionPage';
import ClientDeviceInfoPage from './ClientDeviceInfoPage';
import ClientDevicesPage from './ClientDevicesPage';
import ClientHomePage from './ClientHomePage';
import ClientNotificationsPage from './ClientNotificationsPage';
import ClientAppPage from './ClientAppPage';

const ClientPage = props => {
  const [isOpen, setIsOpen] = useState(false);
  const [messageNotification, setMessageNotification] = useState('');

  const idUser = useSelector(state => state.auth.id);

  const connectToSocket = useCallback(() => {
    const url = `${process.env.REACT_APP_API_URL}/socket`;
    const websocket = new SockJS(url);
    const stompClient = Stomp.over(websocket);

    stompClient.connect({}, frame => {
      stompClient.subscribe(
        `/topic/socket/notification/measurement/exceed/user/${idUser}`,
        notification => {
          let message = notification.body;
          setMessageNotification(message);
          setIsOpen(true);
        }
      );
    });
  }, [idUser]);

  useEffect(() => {
    connectToSocket();
  });

  const handleOnCloseNotification = () => {
    setIsOpen(false);
  };

  return (
    <React.Fragment>
      <NavBar role="CLIENT">
        <Router history={history}>
          <Notification
            message={messageNotification}
            isOpen={isOpen}
            onClose={handleOnCloseNotification}
          />
          <Switch>
            <Route path="/client" exact component={ClientHomePage} />
            <Route
              path="/client/my-devices"
              exact
              component={ClientDevicesPage}
            />
            <Route
              path="/client/my-devices/:id"
              exact
              component={ClientDeviceInfoPage}
            />
            <Route
              path="/client/notification"
              exact
              component={ClientNotificationsPage}
            />
            <Route
              path="/client/consumption"
              exact
              component={ClientConsumptionPage}
            />
            <Route path="/client/my-app" exact component={ClientAppPage} />
            <Redirect to="/client" />
          </Switch>
        </Router>
      </NavBar>
    </React.Fragment>
  );
};

export default requireAuth(ClientPage);
