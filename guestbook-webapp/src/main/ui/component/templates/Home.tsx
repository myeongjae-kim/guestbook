import MentionTableContainer from 'main/ui/container/organisms/MentionTableContainer';
import React from 'react';
import withStyles, { WithStyles } from "react-jss";

const styles = {
  home: {
    textAlign: 'center'
  },
  header: {
    fontSize: 60,
    lineHeight: '1em',
    fontWeight: 400,
    fontFamily: '"BM HANNA 11yrs old", sans-serif',
    wordSpacing: '-0.06em',
    letterSpacing: '-0.025em',
    textShadow: '2px 2px #ccc',
    padding: '40px 0 10px 0'
  },
  headerText: {
    cursor: 'pointer'
  },
  tableWrapper: {
    padding: 20,
    maxWidth: 600,
    display: 'flex',
    justifyContent: 'center',
    margin: 'auto'
  }
}

interface IProps extends WithStyles<typeof styles> { }

const Home: React.FC<IProps> = ({ classes }: IProps) =>
  <div className={classes.home}>
    <header className={classes.header}>
      <span className={classes.headerText} onClick={refresh}>달 방명록</span>
    </header>
    <div className={classes.tableWrapper}>
      <MentionTableContainer />
    </div>
  </div>

const refresh = () => window.location.reload()

export default withStyles(styles)(Home);
