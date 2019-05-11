import IMentionResponse from "main/api/mentions/dto/IMentionResponse";
import * as React from 'react';
import withStyles, { WithStyles } from "react-jss";

const styles = {
  tableRow: {
    display: 'flex'
  }
}

interface IProps extends WithStyles<typeof styles> {
  mention: IMentionResponse
}

const MentionTableRow: React.SFC<IProps> = ({ classes, mention }) => {
  const { id, name, content, createdAt } = mention;

  return <div className={classes.tableRow}>
    <div>{id}</div>
    <div>{name}</div>
    <div>{content}</div>
    <div>{createdAt}</div>
  </div>
}


export default withStyles(styles)(MentionTableRow);