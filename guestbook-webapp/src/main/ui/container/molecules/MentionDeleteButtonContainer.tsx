import MentionDeleteButton from 'main/ui/component/molecules/MentionDeleteButton';
import * as deleteButtonModule from 'main/ui/modules/mentions/delete-button';
import * as React from 'react';
import { connect } from 'react-redux';
import { bindActionCreators, Dispatch } from 'redux';

interface IProps {
  id: number
  dispatcher: typeof deleteButtonModule
}

const MentionDeleteButtonContainer: React.FC<IProps> = ({ id, dispatcher }) => {
  const deleteMention = () => {
    if (window.confirm('글을 지우시겠습니까?')) {
      dispatcher.deleteMention(id);
    }
    return;
  }
  return <MentionDeleteButton deleteMention={deleteMention} />;
}

const mapDispatchToProps = (dispatch: Dispatch<deleteButtonModule.Action>) => ({
  dispatcher: bindActionCreators(deleteButtonModule, dispatch)
})

export default connect(null, mapDispatchToProps)(MentionDeleteButtonContainer);