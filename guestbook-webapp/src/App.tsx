import React from 'react';
import logo from './logo.svg';
// import './App.css';

import withStyles, { WithStyles } from "react-jss"

const styles = {
  App: {
    textAlign: "center"
  },
  AppLogo: {
    height: "40vmin",
    pointerEvents: "none"
  },
  AppHeader: {
    backgroundColor: "#282c34",
    minHeight: "100vh",
    display: "flex",
    flexDirection: "column",
    alignItems: "center",
    justifyContent: "center",
    fontSize: "calc(10px + 2vmin)",
    color: "white"
  },
  AppLink: {
    color: "#61dafb"
  }
}

interface IProps extends WithStyles<typeof styles> { }

const App: React.FC<IProps> = (props: IProps) => {
  const { classes } = props;
  return (
    <div className={classes.App}>
      <header className={classes.AppHeader}>
        <img src={logo} className={classes.AppLogo} alt="logo" />
        <p>
          aaEdit <code>src/App.tsx</code> and save to reload.
        </p>
        <a
          className="App-link"
          href="https://reactjs.org"
          target="_blank"
          rel="noopener noreferrer"
        >
          Learn React
        </a>
      </header>
    </div>
  );
}

export default withStyles(styles)(App);
