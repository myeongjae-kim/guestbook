import * as React from 'react';
import withStyles, { WithStyles } from 'react-jss';
import { Table } from 'semantic-ui-react';

const styles = {
  cell: {
    cursor: 'default !important'
  }
}

interface IProps extends WithStyles<typeof styles> { }

const MentionTableHeader: React.FC<IProps> = ({ classes }) =>
  <Table.Header>
    <Table.Row>
      <Table.HeaderCell className={classes.cell}>ID</Table.HeaderCell>
      <Table.HeaderCell className={classes.cell} width={3}>이름</Table.HeaderCell>
      <Table.HeaderCell className={classes.cell} width={6}>내용</Table.HeaderCell>
      <Table.HeaderCell className={classes.cell}>작성일시</Table.HeaderCell>
      <Table.HeaderCell className={classes.cell} width={2} textAlign="center">수정/삭제&nbsp;</Table.HeaderCell>
    </Table.Row>
  </Table.Header>

export default withStyles(styles)(MentionTableHeader);