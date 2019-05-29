import ICommentPostRequest from 'main/api/comments/dto/ICommentPostRequest';
import * as React from 'react';
import withStyles, { WithStyles } from 'react-jss';
import { Button, Form, Icon, Input, Table, TextArea, TextAreaProps } from 'semantic-ui-react';

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
  },
  row: {
    background: "#fafafa"
  }
}

interface IProps extends WithStyles<typeof styles> {
  commentPostRequest: ICommentPostRequest
  postComment(): void
  changeName(e: React.ChangeEvent<HTMLInputElement>): void
  changeContent(_: React.FormEvent<HTMLTextAreaElement>, data: TextAreaProps): void
}

const CommentAddForm: React.FC<IProps> = ({
  classes,
  commentPostRequest,
  postComment,
  changeName,
  changeContent
}) => {
  const { name, content } = commentPostRequest;
  return <Table.Row className={classes.row}>
    <Table.Cell>
      <Icon name="level up alternate" style={{
        transform: 'rotate(90deg)',
        marginLeft: 10
      }} />
    </Table.Cell>
    <Table.Cell>
      <Input className={classes.nameInput} value={name} onChange={changeName} />
    </Table.Cell>
    <Table.Cell colSpan={2}>
      <Form>
        <TextArea className={classes.contentInput} value={content} onChange={changeContent} />
      </Form>
    </Table.Cell>
    <Table.Cell textAlign="center">
      <Button icon="check" onClick={postComment} />
    </Table.Cell>
  </Table.Row>
}

export default withStyles(styles)(CommentAddForm);