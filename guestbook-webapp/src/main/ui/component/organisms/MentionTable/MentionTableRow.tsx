import IMentionResponse from "main/api/mentions/dto/IMentionResponse";
import * as React from 'react';
import withStyles, { WithStyles } from "react-jss";
import { Table } from "semantic-ui-react";

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

  return <Table.Row>
    <Table.Cell>{id}</Table.Cell>
    <Table.Cell>{name}</Table.Cell>
    <Table.Cell>{content}</Table.Cell>
    <Table.Cell>{createdAt}</Table.Cell>
  </Table.Row>
}


export default withStyles(styles)(MentionTableRow);