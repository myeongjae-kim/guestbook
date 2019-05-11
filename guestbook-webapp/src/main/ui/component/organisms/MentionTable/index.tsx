import IMentionResponse from 'main/api/mentions/dto/IMentionResponse';
import * as React from 'react';
import withStyles, { WithStyles } from 'react-jss';
import MentionTableHeader from './MentionTableHeader';
import MentionTableRow from './MentionTableRow';

const styles = {
  tableWrapper: {
    width: 500,
    height: 500
  }
}

interface IProps extends WithStyles<typeof styles> {
  mentions: IMentionResponse[]
}

const MentionTable: React.FC<IProps> = ({ classes, mentions }) =>
  <div className={classes.tableWrapper}>
    <MentionTableHeader />
    {mentions.map((mention, index) =>
      <MentionTableRow mention={mention} key={index} />
    )}
  </div>

export default withStyles(styles)(MentionTable);