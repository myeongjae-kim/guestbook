import IMentionResponse from "main/api/mentions/dto/IMentionResponse";
import MentionDeleteButtonContainer from "main/ui/container/molecules/MentionDeleteButtonContainer";
import * as React from 'react';
import { Table } from "semantic-ui-react";

interface IProps {
  mention: IMentionResponse
}

const MentionTableRow: React.SFC<IProps> = ({ mention }) => {
  const { id, name, content, createdAt } = mention;

  return <Table.Row>
    <Table.Cell>{id}</Table.Cell>
    <Table.Cell>{name}</Table.Cell>
    <Table.Cell>{content}</Table.Cell>
    <Table.Cell>{createdAt}</Table.Cell>
    <Table.Cell><MentionDeleteButtonContainer id={id} /></Table.Cell>
  </Table.Row>
}


export default MentionTableRow;