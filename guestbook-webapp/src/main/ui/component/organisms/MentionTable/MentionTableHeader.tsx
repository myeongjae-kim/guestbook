import * as React from 'react';
import withStyles, { WithStyles } from 'react-jss';

const styles = {
  tableHeader: {
    display: 'flex'
  }
}

interface IProps extends WithStyles<typeof styles> { }

const MentionTableHeader: React.FC<IProps> = ({ classes }) => {
  return <div className={classes.tableHeader}>
    <div>ID</div>
    <div>이름</div>
    <div>내용</div>
    <div>작성일시</div>
  </div>;
}

export default withStyles(styles)(MentionTableHeader);