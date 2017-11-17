package com.kirillstom.walkmetest.ui.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.kirillstom.walkmetest.R;
import com.kirillstom.walkmetest.database.FormulaEntity;
import com.kirillstom.walkmetest.databinding.ItemFormulaBinding;

import java.util.List;

/**
 * Created by kirill.stom on 17/11/2017.
 */

public class FormulaAdapter extends RecyclerView.Adapter<FormulaAdapter.ViewHolder> {

    private List<FormulaEntity> mFormulas;

    public void setFormulas(List<FormulaEntity> formulas) {
        if (mFormulas == null) {
            mFormulas = formulas;
            notifyItemRangeInserted(0, formulas.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return mFormulas.size();
                }

                @Override
                public int getNewListSize() {
                    return formulas.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return TextUtils.equals(mFormulas.get(oldItemPosition).getFormulaInfix(),
                            formulas.get(newItemPosition).getFormulaInfix());
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    return mFormulas.get(oldItemPosition).equals(formulas.get(newItemPosition));
                }
            });
            mFormulas = formulas;
            result.dispatchUpdatesTo(this);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemFormulaBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_formula, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        FormulaEntity formulaEntity = mFormulas.get(position);
        holder.mBinding.formulaInfixTextView.setText(formulaEntity.getFormulaInfix());
        holder.mBinding.formulaResultTextView.setText(formulaEntity.getResult());
    }

    @Override
    public int getItemCount() {
        return mFormulas.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ItemFormulaBinding mBinding;

        public ViewHolder(ItemFormulaBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }
    }
}
