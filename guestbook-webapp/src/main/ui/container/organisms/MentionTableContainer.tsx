import IMentionResponse from 'main/api/mentions/dto/IMentionResponse';
import MentionTable from 'main/ui/component/organisms/MentionTable';
import { IRootState } from 'main/ui/modules';
import * as tableModule from 'main/ui/modules/mentions/table';
import * as React from 'react';
import { connect } from 'react-redux';
import { bindActionCreators, Dispatch } from 'redux';

interface IProps {
  mentions: IMentionResponse[]
  pending: boolean
  rejected: boolean
  dispatcher: typeof tableModule
}

class MentionTableContainer extends React.Component<IProps> {
  public async componentDidMount() {
    this.props.dispatcher.getMentionList()
  }

  public render() {
    const { mentions, pending } = this.props;
    return <div style={{ opacity: pending ? 0.5 : 'initial' }}>
      <MentionTable mentions={mentions} />
    </div>
  }
}

const mapStateToProps = ({ mentions }: IRootState) => ({
  mentions: mentions.table.get("mentions"),
  pending: mentions.table.get("pending"),
  rejected: mentions.table.get("rejected")
})

const mapDispatchToProps = (dispatch: Dispatch<tableModule.Action>) => ({
  dispatcher: bindActionCreators(tableModule, dispatch)
})

export default connect(mapStateToProps, mapDispatchToProps)(MentionTableContainer);