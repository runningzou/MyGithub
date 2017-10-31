package com.runningzou.mygithub.recyclerview;

import android.databinding.ViewDataBinding;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.runningzou.mygithub.util.RxCommand;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by zouzhihao on 2017/10/13.
 */

public abstract class BaseAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {

    public static final String TAG = BaseAdapter.class.getSimpleName();

    protected List<T> mItems = new ArrayList<>();
    protected List<T> mUpdateItems;

    private int dataVersion = 0;

    protected RecyclerView mRecyclerView;
    protected LinearLayoutManager mLinearLayoutManager;

    private RxCommand<DiffResultWrapper> mDiffResultWrapperRxCommand;

    public BaseAdapter() {

        mDiffResultWrapperRxCommand = RxCommand.create(new Function<Object, Observable<DiffResultWrapper>>() {
            @Override
            public Observable<DiffResultWrapper> apply(@NonNull final Object o) throws Exception {
                if (o instanceof BaseAdapter.DiffResultWrapper) {

                    return Observable.create(new ObservableOnSubscribe<DiffResultWrapper>() {
                        @Override
                        public void subscribe(@NonNull ObservableEmitter<DiffResultWrapper> e) throws Exception {
                            DiffResultWrapper wrapper = (DiffResultWrapper) o;
                            BaseCallBack callBack = new BaseCallBack(mItems, mUpdateItems);
                            DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(callBack, true);
                            wrapper.mDiffResult = diffResult;
                            e.onNext(wrapper);
                            e.onComplete();
                        }
                    })
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread());

                } else {
                    throw new RuntimeException("must pass DiffResultWrapper to excute()");
                }
            }
        });


        mDiffResultWrapperRxCommand.switchToLatest()
                .subscribe(new Consumer<DiffResultWrapper>() {
                    @Override
                    public void accept(DiffResultWrapper diffResultWrapper) throws Exception {
                        if (diffResultWrapper.mDataVersion != dataVersion) {
                            return;
                        }

                        diffResultWrapper.mDiffResult.dispatchUpdatesTo(BaseAdapter.this);

                        mItems = mUpdateItems;

                        if (diffResultWrapper.mRefresh) {
                            mLinearLayoutManager.scrollToPosition(0);
                        }
                    }
                });
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewDataBinding binding = createBinding(parent, viewType);
        return new BaseViewHolder(binding);
    }

    protected abstract ViewDataBinding createBinding(ViewGroup parent, int viewType);

    protected abstract void bind(ViewDataBinding binding, T item);

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {

        if (position < mItems.size()) {
            bind(holder.binding, mItems.get(position));
            holder.binding.executePendingBindings();
        }

    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

        mRecyclerView = recyclerView;
        mLinearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

    }

    //更新数据
    private void replace(final List<T> update, final boolean refresh) {

        mUpdateItems = update;

        dataVersion++;
        if (mItems == null) {
            if (update == null) {
                return;
            }
            mItems = update;
            notifyDataSetChanged();
        } else if (update == null) {
            int oldSize = mItems.size();
            mItems = null;
            notifyItemRangeRemoved(0, oldSize);
        } else {

            DiffResultWrapper wrapper = new DiffResultWrapper(dataVersion, refresh);
            mDiffResultWrapperRxCommand.execute(wrapper);

        }
    }

    public void loadMore(List<T> events) {
        List<T> result = new ArrayList<>();
        result.addAll(mItems);
        result.addAll(events);
        replace(result, false);

    }

    public void refresh(List<T> events) {
        replace(events, true);
    }

    public void setItems(List<T> events) {
        mItems = events;
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }


    protected boolean areItemsTheSame(T oldItem, T newItem) {
        return oldItem == newItem;
    }

    protected boolean areContentsTheSame(T oldItem, T newItem) {
        return oldItem.equals(newItem);
    }

    public class BaseCallBack extends DiffUtil.Callback {

        private List<T> mOldItems;
        private List<T> mUpdateItems;

        public BaseCallBack(List<T> oldItems, List<T> updateItems) {
            mOldItems = oldItems;
            mUpdateItems = updateItems;
        }

        @Override
        public int getOldListSize() {
            return mOldItems.size();
        }

        @Override
        public int getNewListSize() {
            return mUpdateItems.size();
        }

        @Override
        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
            return BaseAdapter.this.areItemsTheSame(mOldItems.get(oldItemPosition), mUpdateItems.get(newItemPosition));
        }

        @Override
        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
            return BaseAdapter.this.areContentsTheSame(mOldItems.get(oldItemPosition), mUpdateItems.get(newItemPosition));
        }
    }


    private class DiffResultWrapper {

        public int mDataVersion;
        public boolean mRefresh;
        public DiffUtil.DiffResult mDiffResult;

        public DiffResultWrapper(int dataVersion, boolean refresh) {
            mDataVersion = dataVersion;
            mRefresh = refresh;
        }
    }

}
