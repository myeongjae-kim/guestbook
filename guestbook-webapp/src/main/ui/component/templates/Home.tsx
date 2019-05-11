import React from 'react';
import withStyles, { WithStyles } from "react-jss"

const styles = {
  Home: {
    textAlign: "center"
  },
  HomeHeader: {
    backgroundColor: "#282c34",
    minHeight: "100vh",
    display: "flex",
    flexDirection: "column",
    alignItems: "center",
    justifyContent: "center",
    fontSize: "calc(10px + 2vmin)",
    color: "white"
  },
  HomeLink: {
    color: "#61dafb"
  }
}

interface IProps extends WithStyles<typeof styles> { }

const Home: React.FC<IProps> = ({ classes }: IProps) =>
  <div className={classes.Home}>
    <header className={classes.HomeHeader}>
      <p>
        Edit <code>src/Home.tsx</code> and save to reload.
        </p>
      <a
        className="Home-link"
        href="https://reactjs.org"
        target="_blank"
        rel="noopener noreferrer"
      >
        Learn React
        </a>
    </header>
  </div>

export default withStyles(styles)(Home);
