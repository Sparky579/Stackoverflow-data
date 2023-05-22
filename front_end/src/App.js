import React from "react";
import { Layout, Menu } from "antd";
import {
  BrowserRouter as Router,
  Switch,
  Route,
  Link
} from "react-router-dom";

import Home from "./pages/Home.js";
import "./App.css"
import AcceptedAnswers from "./pages/AcceptedAnswers.js";
import Tags from "./pages/Tags"
import User from "./pages/User"
import Andvance from "./pages/Advance"

const { Header, Content, Footer } = Layout;

const App = () => {
  return (
    <Router>
      <Layout className="layout">
        <Header>
          <div className="logo" />
          <Menu theme="dark" mode="horizontal" defaultSelectedKeys={["1"]}>
            <Menu.Item key="1"><Link to="/">Number of Answers</Link></Menu.Item>
            <Menu.Item key="2"><Link to="/accept_answer">Accepted Answers</Link></Menu.Item>
            <Menu.Item key="3"><Link to="/tags">Tags</Link></Menu.Item>
            <Menu.Item key="4"><Link to="/user">User</Link></Menu.Item>
            <Menu.Item key="5"><Link to="/advance">Advance</Link></Menu.Item>
          </Menu>
        </Header>
        <Content style={{ padding: "0 50px" }}>
          <div className="site-layout-content">
            <Switch>
              <Route exact path="/">
                <Home/>
              </Route>
              <Route exact path="/accept_answer">
                <AcceptedAnswers/>
              </Route>
              <Route exact path="/tags">
                <Tags/>
              </Route>
              <Route exact path="/user">
                <User/>
              </Route>
              <Route exact path="/advance">
                <Andvance/>
              </Route>

            </Switch>
          </div>
        </Content>
        <Footer style={{ textAlign: "center" }}>
          SUSTech CS209A©2023 Created by fei
        </Footer>
      </Layout>
    </Router>
  );
};

export default App;
