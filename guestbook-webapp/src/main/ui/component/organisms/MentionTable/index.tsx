import IMentionResponse from 'main/api/mentions/dto/IMentionResponse';
import * as React from 'react';
import withStyles, { WithStyles } from 'react-jss';
import { Table } from 'semantic-ui-react';
import MentionTableBody from './MentionTableBody';
import MentionTableHeader from './MentionTableHeader';

const styles = {
  tableFont: {
    fontSize: "1.2em !important",
    lineHeight: "1.6em",
    letterSpacing: "-0.03em"
  }
}

interface IProps extends WithStyles<typeof styles> {
  mentions: IMentionResponse[]
}

const MentionTable: React.FC<IProps> = ({ classes, mentions }) =>
  <Table selectable unstackable className={classes.tableFont}>
    <MentionTableHeader />
    <MentionTableBody mentions={mentions} />
  </Table>

export default withStyles(styles)(MentionTable);