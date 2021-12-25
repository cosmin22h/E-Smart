import React from 'react';
import { Router, Route, Switch, Redirect } from 'react-router-dom';
import { connect } from 'react-redux';

import history from 'utils/history';
import { signIn } from 'store/actions';

import LoginPage from 'pages/auth/SignInPage';
import AdminPage from 'pages/admin/AdminPage';
import ClientPage from 'pages/client/ClientPage';

const App = props => {
  const { signIn, auth } = props;

  const autoLogin = () => {
    const token = localStorage.getItem('token');
    if (token) {
      const tokenDetails = JSON.parse(token);
      signIn(tokenDetails);
    }
  };

  if (!auth.id) {
    autoLogin();
  }

  return (
    <React.Fragment>
      <Router history={history}>
        <Switch>
          <Route path="/admin" component={AdminPage} />
          <Route path="/client" component={ClientPage} />
          <Route path="/sign-in" exact component={LoginPage} />
          <Redirect to="/sign-in" />
        </Switch>
      </Router>
    </React.Fragment>
  );
};

const mapStateToProps = state => {
  return { auth: state.auth };
};

export default connect(mapStateToProps, { signIn })(App);
