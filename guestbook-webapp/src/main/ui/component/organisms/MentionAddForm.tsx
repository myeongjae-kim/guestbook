import IMentionRequest from 'main/api/mentions/dto/IMentionRequest';
import * as React from 'react';
import { ChangeEvent } from 'react';
import withStyles, { WithStyles } from 'react-jss';
import { Button, Input, Table } from 'semantic-ui-react';

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
  mentionRequest: IMentionRequest
  postMention(): void
  changeName(e: ChangeEvent<HTMLInputElement>): void
  changeContent(e: ChangeEvent<HTMLInputElement>): void
}

const MentionAddForm: React.FC<IProps> = ({
  classes,
  mentionRequest,
  postMention,
  changeName,
  changeContent
}) => {
  const { name, content } = mentionRequest;
  return <Table unstackable>
    <Table.Header>
      <Table.Row className={classes.center}>
        <Table.HeaderCell>이름</Table.HeaderCell>
        <Table.HeaderCell>내용</Table.HeaderCell>
        <Table.HeaderCell>등록</Table.HeaderCell>
      </Table.Row>
    </Table.Header>
    <Table.Body>
      <Table.Row>
        <Table.Cell><Input className={classes.nameInput} value={name} onChange={changeName} /></Table.Cell>
        <Table.Cell><Input className={classes.contentInput} value={content} onChange={changeContent} /></Table.Cell>
        <Table.Cell><Button icon="add" onClick={postMention} /></Table.Cell>
      </Table.Row>
    </Table.Body>
  </Table>
}

export default withStyles(styles)(MentionAddForm);