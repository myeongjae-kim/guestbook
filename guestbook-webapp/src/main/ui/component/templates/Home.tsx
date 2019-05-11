import MentionTableContainer from 'main/ui/container/organisms/MentionTableContainer';
import React from 'react';
import withStyles, { WithStyles } from "react-jss";

const styles = {
  home: {
    textAlign: "center"
  },
  header: {
  },
}

interface IProps extends WithStyles<typeof styles> { }

const Home: React.FC<IProps> = ({ classes }: IProps) =>
  <div className={classes.home}>
    <header className={classes.header}>
      <span>달 방명록</span>
    </header>
    <MentionTableContainer />
  </div>

export default withStyles(styles)(Home);
