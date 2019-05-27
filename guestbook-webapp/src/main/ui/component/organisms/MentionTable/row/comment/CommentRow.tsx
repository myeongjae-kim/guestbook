import ICommentResponse from "main/api/comments/dto/ICommentResponse";
import CommentDeleteButtonContainer from "main/ui/container/molecules/CommentDeleteButtonContainer";
import * as React from 'react';
import { Button, Icon, Table } from "semantic-ui-react";

interface IProps {
  comment: ICommentResponse
  refreshComments(): void
}

const CommentRow: React.FC<IProps> = ({ comment, refreshComments }) => {
  const { id, name, content, createdAt } = comment;

  return <>
    <Table.Row style={{
      background: "#F4F4F4"
    }}>
      <Table.Cell>
        <Icon name="level up alternate" style={{
          transform: 'rotate(90deg)',
          marginLeft: 10
        }} />
      </Table.Cell>
      <Table.Cell>{name}</Table.Cell>
      <Table.Cell>{content}</Table.Cell>
      <Table.Cell>{createdAt}</Table.Cell>
      <Table.Cell>
        <Button.Group>
          <Button icon="edit" />
          <CommentDeleteButtonContainer id={id} refreshComments={refreshComments} />
        </Button.Group>
      </Table.Cell>
    </Table.Row>
  </>
}


export default CommentRow;