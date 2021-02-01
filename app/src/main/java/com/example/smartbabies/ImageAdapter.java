package com.example.smartbabies;
import android.content.Context;
        import android.view.ContextMenu;
        import android.view.LayoutInflater;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ImageView;
        import android.widget.TextView;

        import androidx.recyclerview.widget.RecyclerView;

        import com.bumptech.glide.Glide;
        import com.squareup.picasso.Picasso;

        import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {
    private Context mContext;
    private List<upload> muploads;
    private onItemclickListener mListener;

    public ImageAdapter(Context context, List<upload>uploads){
        mContext=context;
        muploads=uploads;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(mContext).inflate(R.layout.sellinglists,parent,false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        upload uploadCurrent=muploads.get(position);
        holder.Product.setText(uploadCurrent.getPname());
        holder.Description1.setText(uploadCurrent.getDescription());
        holder.Price.setText(uploadCurrent.getPrice());
        Glide.with(mContext).load(uploadCurrent.getImageUrl()).placeholder(R.mipmap.defaultimg)
                .centerInside().into(holder.imageView);


    }

    @Override
    public int getItemCount() {
        return muploads.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {
        public TextView Product;
        public TextView Description1;
        public TextView   Price;
        public ImageView imageView;
        public ImageViewHolder(View itemView) {
            super(itemView);
            Product=itemView.findViewById(R.id.a);
            Description1=itemView.findViewById(R.id.b);
            Price=itemView.findViewById(R.id.c);
            imageView=itemView.findViewById(R.id.post_image);

            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onClick(View view) {
            if(mListener!=null){
                int position = getAdapterPosition();
                if(position != RecyclerView.NO_POSITION){
                    mListener.onItemClick(position);
                }

            }

        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            contextMenu.setHeaderTitle("select Action");
            MenuItem dowhatever= contextMenu.add(Menu.NONE,1,1,"Do whatever");
            MenuItem delete= contextMenu.add(Menu.NONE,2,2,"Delete");
            dowhatever.setOnMenuItemClickListener(this);
            delete.setOnMenuItemClickListener(this);
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            if(mListener!=null){
                int position = getAdapterPosition();
                if(position != RecyclerView.NO_POSITION){
                    switch (menuItem.getItemId()){
                        case 1:
                            mListener.onWhatEverClick(position);
                            return true;
                        case 2:
                            mListener.onDeleteClick(position);
                            return true;
                    }
                }

            }
            return false;
        }
    }
    public interface onItemclickListener{
        void onItemClick(int position);
        void onWhatEverClick(int position);
        void onDeleteClick(int position);
    }
    public void setOnItemClickListener(onItemclickListener listener){
        mListener=listener;
    }
}
