import IMentionRequest from 'main/api/mentions/dto/IMentionRequest';
import IMentionResponse from 'main/api/mentions/dto/IMentionResponse';
import * as React from 'react';
import { ChangeEvent } from 'react';
import withStyles, { WithStyles } from 'react-jss';
import { Button, Form, Input, Table, TextArea, TextAreaProps } from 'semantic-ui-react';

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
  oldMention: IMentionResponse
  mentionRequest: IMentionRequest
  putMention(): void
  changeName(e: ChangeEvent<HTMLInputElement>): void
  changeContent(_: React.FormEvent<HTMLTextAreaElement>, data: TextAreaProps): void
}

const MentionEditForm: React.FC<IProps> = ({
  classes,
  oldMention,
  mentionRequest,
  putMention,
  changeName,
  changeContent
}) => {
  const { name, content } = mentionRequest;
  return <Table.Row>
    <Table.Cell>{oldMention.id}</Table.Cell>
    <Table.Cell><Input className={classes.nameInput} value={name} onChange={changeName} /></Table.Cell>
    <Table.Cell>
      <Form>
        <TextArea className={classes.contentInput} value={content} onChange={changeContent} />
      </Form>
    </Table.Cell>
    <Table.Cell>{oldMention.createdAt}</Table.Cell>
    <Table.Cell textAlign="center">
      <Button icon="check" onClick={putMention} />
    </Table.Cell>
  </Table.Row>
}

export default withStyles(styles)(MentionEditForm);