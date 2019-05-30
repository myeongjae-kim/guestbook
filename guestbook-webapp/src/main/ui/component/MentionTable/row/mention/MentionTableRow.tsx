import IMentionResponse from "main/api/mentions/dto/IMentionResponse";
import MentionDeleteButtonContainer from "main/ui/container/DeleteButtonContainer/MentionDeleteButtonContainer";
import MentionEditFormContainer from "main/ui/container/MentionTableContainer/row/mention/MentionEditFormContainer";
import * as React from 'react';
import withStyles, { WithStyles } from "react-jss";
import { Button, Table } from "semantic-ui-react";

const styles = {
  mention: {
    '&:hover': {
      background: "#fafafa"
    }
  }
}

interface IProps extends WithStyles<typeof styles> {
  mention: IMentionResponse
}

const MentionTableRow: React.SFC<IProps> = ({ classes, mention }) => {
  const { id, name, content, createdAt } = mention;

  const [isEditing, setIsEditing] = React.useState(false);
  const edit = () => setIsEditing(true)
  const finishEditing = () => setIsEditing(false)

  if (isEditing) {
    return <MentionEditFormContainer
      oldMention={mention}
      finishEditing={finishEditing} />
  }

  return <>
    <Table.Row className={classes.mention} >
      <Table.Cell>{id}</Table.Cell>
      <Table.Cell>{name}</Table.Cell>
      <Table.Cell>{content}</Table.Cell>
      <Table.Cell>{createdAt}</Table.Cell>
      <Table.Cell>
        <Button.Group>
          <Button icon="edit" onClick={edit} />
          <MentionDeleteButtonContainer id={id} />
        </Button.Group>
      </Table.Cell>
    </Table.Row>
  </>
}


export default withStyles(styles)(MentionTableRow);