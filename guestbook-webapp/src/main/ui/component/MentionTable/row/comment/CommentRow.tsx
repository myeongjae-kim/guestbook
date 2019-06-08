import ICommentResponse from "main/api/comments/dto/ICommentResponse";
import CommentDeleteButtonContainer from "main/ui/container/DeleteButtonContainer/CommentDeleteButtonContainer";
import CommentEditFormContainer from "main/ui/container/MentionTableContainer/row/comment/CommentEditFormContainer";
import * as React from 'react';
import { Button, Icon, Table } from "semantic-ui-react";

interface IProps {
  comment: ICommentResponse
  refreshComments(): void
}

const CommentRow: React.FC<IProps> = ({ comment, refreshComments }) => {
  const { id, name, content, createdAt } = comment;
  const [isEditing, setIsEditing] = React.useState(false);
  const edit = () => setIsEditing(true)
  const finishEditing = () => setIsEditing(false)


  if (isEditing) {
    return <CommentEditFormContainer
      oldComment={comment}
      finishEditing={finishEditing}
      refreshComments={refreshComments} />
  }

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
          <Button icon="edit" onClick={edit} />
          <CommentDeleteButtonContainer id={id} refreshComments={refreshComments} />
        </Button.Group>
      </Table.Cell>
    </Table.Row>
  </>
}


export default CommentRow;