import React from 'react';
import { Router, Route, Switch, Redirect } from 'react-router-dom';

import history from 'utils/history';

import requireAuth from 'components/guards/requireAuth';
import NavBar from 'components/navigation/NavBar';
import AdminDashboardPage from './AdminDashboardPage';
import CreateUserPage from './CreateUserPage';
import AdminClientsListPage from './AdminClientsListPage';
import EditUserPage from './EditUserPage';
import ChangePasswordPage from './ChangePasswordPage';
import AdminDevicesListPage from './AdminDevicesListPage';
import AdminClientDevicesListPage from './AdminClientDevicesListPage';
import AdminProfilePage from './AdminProfilePage';
import CreateDeviceForClientPage from './CreateDeviceForClientPage';
import EditDevicePage from './EditDevicePage';
import CreateDevicePage from './CreateDevice';
import SensorsExportPage from './SensorsExportPage';

const AdminPage = props => {
  return (
    <React.Fragment>
      <NavBar role="ADMIN">
        <Router history={history}>
          <Switch>
            <Route path="/admin" exact component={AdminDashboardPage} />
            {/* CLIENTS */}
            <Route
              path="/admin/clients"
              exact
              component={AdminClientsListPage}
            />
            <Route path="/admin/clients/add" exact component={CreateUserPage} />
            <Route
              path="/admin/clients/update/:id"
              exact
              component={EditUserPage}
            />
            <Route
              path="/admin/clients/devices/:id"
              exact
              component={AdminClientDevicesListPage}
            />
            {/* DEVICES */}
            <Route
              path="/admin/devices"
              exact
              component={AdminDevicesListPage}
            />
            <Route
              path="/admin/devices/add"
              exact
              component={CreateDevicePage}
            />
            <Route
              path="/admin/devices/add/:id"
              exact
              component={CreateDeviceForClientPage}
            />
            <Route
              path="/admin/devices/update/:id/:idDevice"
              exact
              component={EditDevicePage}
            />
            {/* ADMIN PROFILE */}
            <Route
              path="/admin/my-profile"
              exact
              component={AdminProfilePage}
            />
            {/* CHANGE PASSWORD */}
            <Route
              path="/admin/change-password/:id"
              exact
              component={ChangePasswordPage}
            />
            {/* SENSORS */}
            <Route
              path="/admin/sensor/export"
              exact
              component={SensorsExportPage}
            />
            <Redirect to="/admin" />
          </Switch>
        </Router>
      </NavBar>
    </React.Fragment>
  );
};

export default requireAuth(AdminPage);
