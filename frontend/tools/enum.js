

export const ArticleStatus = Object.freeze({
    DRAFT: { label: '草稿', color: 'success'},
    PUBLISH: { label: '发布', color: ''},
    PRIVATE: { label: '私有', color: ''},
    DELETE: { label: '已删除', color: ''},
    PENDING: { label: '待处理', color: 'warning'},
})

export const ArticleCommentStatus = Object.freeze({
    OPEN: { label: '打开', color: ''},
    CLOSE: { label: '关闭', color: 'info'},
    READ_ONLY: { label: '只读', color: 'danger'},
})

// 0 上 1 左 2 右
// private int coverPosition;
export const ArticleCoverPosition = Object.freeze({
    0: { label: '顶部', color: ''},
    1: { label: '左', color: ''},
    2: { label: '右', color: ''}
})
