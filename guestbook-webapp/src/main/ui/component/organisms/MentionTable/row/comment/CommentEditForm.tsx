import ICommentPutRequest from 'main/api/comments/dto/ICommentPutRequest';
import ICommentResponse from 'main/api/comments/dto/ICommentResponse';
import * as React from 'react';
import withStyles, { WithStyles } from 'react-jss';
import { Button, Form, Icon, Table, TextArea, TextAreaProps } from 'semantic-ui-react';

const styles = {
  tableFont: {
    fontSize: "1.2em !important",
    lineHeight: "1.6em",
    letterSpacing: "-0.03em"
  },
  center: {
    textAlign: "center"
  },
  nameInput: {
    width: 100
  },
  contentInput: {
    width: 250
  }
}

interface IProps extends WithStyles<typeof styles> {
  oldComment: ICommentResponse
  commentPutRequest: ICommentPutRequest
  putComment(): void
  changeContent(_: React.FormEvent<HTMLTextAreaElement>, data: TextAreaProps): void
}

const CommentEditForm: React.FC<IProps> = ({
  classes,
  oldComment,
  commentPutRequest,
  putComment,
  changeContent
}) => {
  const { content } = commentPutRequest;
  return <Table.Row>
    <Table.Cell>
      <Icon name="level up alternate" style={{
        transform: 'rotate(90deg)',
        marginLeft: 10
      }} />
    </Table.Cell>
    <Table.Cell>{oldComment.name}</Table.Cell>
    <Table.Cell>
      <Form>
        <TextArea className={classes.contentInput} value={content} onChange={changeContent} />
      </Form>
    </Table.Cell>
    <Table.Cell>{oldComment.createdAt}</Table.Cell>
    <Table.Cell textAlign="center">
      <Button icon="check" onClick={putComment} />
    </Table.Cell>
  </Table.Row>
}

export default withStyles(styles)(CommentEditForm);