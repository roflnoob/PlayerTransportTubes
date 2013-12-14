package com.roflnoob.ptt.tileentity;

import java.util.ArrayList;

import net.minecraft.tileentity.TileEntity;

import com.roflnoob.ptt.PlayerTransportTubes;

public class TileEntityPlayerTransportTube extends TileEntity {

	public ArrayList<TileEntityPlayerTransportTube> network = new ArrayList<TileEntityPlayerTransportTube>();
	public TileEntityPlayerTransportTube networkHost = null;
	public int id;

	public void onPlaced() {
		ArrayList<TileEntityPlayerTransportTube> ptt = this.getAdjacentTubes();
		if (ptt.size() == 0) {
			this.network.add(this);
			this.setNetworkHost(this);
		} else {
			ArrayList<TileEntityPlayerTransportTube> hosts = new ArrayList<TileEntityPlayerTransportTube>();
			for (TileEntityPlayerTransportTube tube : ptt) {
				if (tube.networkHost != null) {
					hosts.add(tube.networkHost);
				}
			}
			if (hosts.size() > 1) {
				this.network.add(this);
				this.networkHost = this;
				for (TileEntityPlayerTransportTube tube : hosts) {
					for (TileEntityPlayerTransportTube tube1 : tube.network) {
						this.network.add(tube1);
						tube1.setNetworkHost(this);
					}
					tube.setNetworkHost(this);
				}
			} else {
				if (hosts.size() > 0) {
					this.setNetworkHost(hosts.get(0));
					this.networkHost.network.add(this);
				} else if (hosts.size() == 0 && this.networkHost != null) {
					this.networkHost.network.remove(this);
					this.network.clear();
					this.id = 0;
					this.networkHost = null;
				}
			}
		}
	}

	public void onBroken() {
		if (this.networkHost == this) {
			ArrayList<TileEntityPlayerTransportTube> ptt = this.getAdjacentTubes();
			for (TileEntityPlayerTransportTube tube : ptt) {
				tube.networkHost = tube;
				ArrayList<TileEntityPlayerTransportTube> ptt1 = tube.getAdjacentTubes();
				for (int x = 0; x < ptt1.size(); x++) {
					ptt1.get(x).setNetworkHost(tube);
				}
				tube.becomeHost();
			}

		}
	}

	private void becomeHost() {
		if (this.network == null)
			this.network = new ArrayList<TileEntityPlayerTransportTube>();
		for (int x = 0; x < this.network.size(); x++) {
			if (this.network.get(x).networkHost != this)
				this.network.remove(this.network.get(x));
		}
	}

	private void setNetworkHost(TileEntityPlayerTransportTube tube) {
		this.networkHost = tube;
		ArrayList<TileEntityPlayerTransportTube> ptt = this.getAdjacentTubes();
		for (TileEntityPlayerTransportTube tuber : ptt) {
			if (tuber.networkHost != tuber && tuber.networkHost != tube) {
				tuber.setNetworkHost(tube);
			}
		}
	}

	public void reassambleNetwork() {
		ArrayList<TileEntityPlayerTransportTube> ptt = this.getAdjacentTubes();
		for (TileEntityPlayerTransportTube tube : ptt) {
			tube.networkHost = this;
		}
	}

	public ArrayList<TileEntityPlayerTransportTube> getAdjacentTubes() {
		boolean ya = this.worldObj.getBlockId(this.xCoord, this.yCoord + 1, this.zCoord) == PlayerTransportTubes.playerTransportTube.blockID;
		boolean yb = this.worldObj.getBlockId(this.xCoord, this.yCoord - 1, this.zCoord) == PlayerTransportTubes.playerTransportTube.blockID;
		boolean xp = this.worldObj.getBlockId(this.xCoord + 1, this.yCoord, this.zCoord) == PlayerTransportTubes.playerTransportTube.blockID;
		boolean xn = this.worldObj.getBlockId(this.xCoord - 1, this.yCoord, this.zCoord) == PlayerTransportTubes.playerTransportTube.blockID;
		boolean zp = this.worldObj.getBlockId(this.xCoord, this.yCoord, this.zCoord + 1) == PlayerTransportTubes.playerTransportTube.blockID;
		boolean zn = this.worldObj.getBlockId(this.xCoord, this.yCoord, this.zCoord - 1) == PlayerTransportTubes.playerTransportTube.blockID;
		ArrayList<TileEntityPlayerTransportTube> ptt = new ArrayList<TileEntityPlayerTransportTube>();
		if (ya) {
			ptt.add((TileEntityPlayerTransportTube) this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord + 1, this.zCoord));
		}
		if (yb) {
			ptt.add((TileEntityPlayerTransportTube) this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord - 1, this.zCoord));
		}
		if (xp) {
			ptt.add((TileEntityPlayerTransportTube) this.worldObj.getBlockTileEntity(this.xCoord + 1, this.yCoord, this.zCoord));
		}
		if (xn) {
			ptt.add((TileEntityPlayerTransportTube) this.worldObj.getBlockTileEntity(this.xCoord - 1, this.yCoord, this.zCoord));
		}
		if (zp) {
			ptt.add((TileEntityPlayerTransportTube) this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord + 1));
		}
		if (zn) {
			ptt.add((TileEntityPlayerTransportTube) this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord - 1));
		}
		return ptt;
	}

	public boolean amIYourHost(TileEntityPlayerTransportTube host) {
		return this.networkHost == host;
	}

	public void dc() {
		if (this.networkHost != null) {
			this.networkHost.network.remove(this);
		}
		this.network.clear();
		this.id = 0;
		this.networkHost = null;
		// ArrayList<TileEntityPlayerTransportTube> ptt =
		// this.getAdjacentTubes();
		// for (int x = 0; x < ptt.size(); x++) {
		// ptt.get(x).dc();
		// }

	}

	public int oldNetworkSize = 0;

	@Override
	public void updateEntity() {

		if (this.networkHost != null && this.networkHost != this) {
			this.network = this.networkHost.network;
			if (!this.networkHost.network.contains(this)) {
				this.networkHost.network.add(this);
			}
			ArrayList<TileEntityPlayerTransportTube> ptt = this.getAdjacentTubes();
			if (ptt.size() == 0) {
				this.networkHost.network.remove(this);
				this.networkHost = null;
			}
			boolean dc = true;
			ArrayList<Integer> ids = new ArrayList<Integer>();
			for (TileEntityPlayerTransportTube tube : ptt) {
				ids.add(tube.id);
			}
			for (int x = 0; x < ids.size(); x++) {
				if (ids.get(x) < this.id && this.id != 0)
					dc = false;
			}
			if (dc) {
				this.dc();
			}
			if (this.networkHost != null && !(this.worldObj.getBlockTileEntity(this.networkHost.xCoord, this.networkHost.yCoord, this.networkHost.zCoord) instanceof TileEntityPlayerTransportTube)) {
				this.networkHost = null;
			}

		}
		if (this.networkHost == this) {
			if (this.oldNetworkSize != this.network.size()) {
				ArrayList<TileEntityPlayerTransportTube> ptt = new ArrayList<TileEntityPlayerTransportTube>();
				ArrayList<TileEntityPlayerTransportTube> ptr = this.getAdjacentTubes();
				ptt.add(this);
				for (int x = 0; x < ptr.size(); x++) {
					ptr.get(x).checkIfConnected(ptt);
				}
				this.oldNetworkSize = this.network.size();
				System.out.println("DETECTED CHANGE IN NETWORK");
			}
			ArrayList<TileEntityPlayerTransportTube> ptt = this.getAdjacentTubes();
			if (ptt.size() == 0) {
				this.network.clear();
				this.network.add(this);
			}
			if (this.network.size() == 0) {
				this.network.add(this);
			}
			for (int x = 0; x < this.network.size(); x++) {
				this.network.get(x).id = x;
				if (!this.network.get(x).amIYourHost(this))
					this.network.remove(this.network.get(x));
			}
			ArrayList<TileEntityPlayerTransportTube> newNetwork = this.network;
			newNetwork.clear();
			newNetwork.add(this);
			for (int x = 1; x < this.network.size(); x++) {
				if (!newNetwork.contains(this.network.get(x)))
					newNetwork.add(this.network.get(x));
			}
			this.network = newNetwork;
		}
		super.updateEntity();
	}

	public void checkIfConnected(ArrayList<TileEntityPlayerTransportTube> connectedTubes) {
		ArrayList<TileEntityPlayerTransportTube> ptt = this.getAdjacentTubes();
		boolean lastTube = true;
		for (int x = 0; x < ptt.size(); x++) {
			TileEntityPlayerTransportTube tube = ptt.get(x);
			if (tube.networkHost == this.networkHost && !connectedTubes.contains(tube)) {
				connectedTubes.add(tube);
				tube.checkIfConnected(connectedTubes);
				lastTube = false;
				break;
			} else if (tube.networkHost == tube) {
				if (tube.network != null && this.networkHost.network.size() > tube.network.size())
					for (int y = 0; y < tube.networkHost.network.size(); y++)
						tube.networkHost.network.get(y).networkHost = this.networkHost;
				else
					for (int y = 0; y < this.networkHost.network.size(); y++)
						this.networkHost.network.get(y).networkHost = tube.networkHost;
			}
		}
		if (lastTube) {
			System.out.println("Last tube after " + (connectedTubes.size() - 1) + " others.");
			boolean isFirst = false;
			for (int x = 1; x < this.networkHost.network.size(); x++) {
				if (!connectedTubes.contains(this.networkHost.network.get(x))) {
					TileEntityPlayerTransportTube tube = this.networkHost.network.get(x);
					if (!isFirst) {
						tube.network = new ArrayList<TileEntityPlayerTransportTube>();
						tube.setNetworkHost(tube);
						tube.network.add(tube);
						isFirst = true;
					}
					this.networkHost.network.remove(tube);
				}
			}
		}
	}
}
